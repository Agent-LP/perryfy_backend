package com.perryfyback.perryfy.controllers.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perryfyback.perryfy.entities.User;
import com.perryfyback.perryfy.models.auth.AuthResponse;
import com.perryfyback.perryfy.models.auth.LoginRequest;
import com.perryfyback.perryfy.models.users.UserRequest;
import com.perryfyback.perryfy.repositories.UserRepository;
import com.perryfyback.perryfy.services.User.StripeUserService;
import com.perryfyback.perryfy.services.jwt.AuthService;
import com.stripe.exception.StripeException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtController{
    

    private final AuthService authService;

    @Autowired
    private StripeUserService stripeUserService;

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest request){
        try {
            AuthResponse response = authService.register(request);
            Optional<User> user = userRepository.findByEmail(request.getEmail());
            if(user.isPresent()){
                // Create customer in Stripe
                var stripeCustomer = stripeUserService.createStripeCustomer(
                    user.get().getEmail(), 
                    user.get().getName() + " " + user.get().getLastname()
                );
                
                // Create payment method in Stripe
                var paymentMethod = stripeUserService.createPaymentMethod();
                
                // Attach payment method to customer
                stripeUserService.attachPaymentMethodToCustomer(
                    paymentMethod.getId(), 
                    stripeCustomer.getId()
                );
                // Save Stripe IDs to user
                user.get().setPaymentMethodToken(paymentMethod.getId());
                userRepository.save(user.get());
            }
            
            System.out.println("Token: " + response.getToken());
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            System.err.println("Error creating user in Stripe: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } 
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Auth endpoint working!");
    }
}