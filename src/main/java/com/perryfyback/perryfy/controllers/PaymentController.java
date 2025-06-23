package com.perryfyback.perryfy.controllers;

import com.perryfyback.perryfy.entities.User;
import com.perryfyback.perryfy.models.users.PaymentRequest;
import com.perryfyback.perryfy.models.users.PaymentResponse;
import com.perryfyback.perryfy.repositories.UserRepository;
import com.perryfyback.perryfy.services.User.StripeUserService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private StripeUserService stripeUserService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            // Get user from database
            Optional<User> userOptional = userRepository.findById(paymentRequest.getUserId().longValue());
            if (userOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            User user = userOptional.get();

            // Get product price from Stripe
            Price price = stripeUserService.getProductPrice(paymentRequest.getStripeProductId());
            if (price == null) {
                return ResponseEntity.badRequest().build();
            }

            // Process payment
            PaymentIntent paymentIntent = stripeUserService.createPayment(
                user.getPaymentMethodToken(),
                price,
                user.getEmail() // Using email to search the stripe customer ID for simplicity
            );

            PaymentResponse response = new PaymentResponse();
            response.setPaymentIntentId(paymentIntent.getId());
            response.setStatus(paymentIntent.getStatus());
            response.setAmount(paymentIntent.getAmount());
            response.setCurrency(paymentIntent.getCurrency());

            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            System.err.println("Payment error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/stripe/products")
    public ResponseEntity<String> getStripeProducts() {
        try {
            String productId = stripeUserService.getProducts();
            return ResponseEntity.ok("First product ID: " + productId);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
} 