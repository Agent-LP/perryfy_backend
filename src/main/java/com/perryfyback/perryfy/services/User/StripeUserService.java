package com.perryfyback.perryfy.services.User;

import com.stripe.model.*;
import com.stripe.param.*;

import jakarta.annotation.PostConstruct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perryfyback.perryfy.config.StripeConfig;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;

@Service
public class StripeUserService {
    @Autowired
    private final StripeConfig stripeConfig; // Spring inyecta aquí 

    public StripeUserService(StripeConfig stripeConfig) { 
        this.stripeConfig = stripeConfig;
    } 
    @PostConstruct 
    void init() { 
        System.out.println("STRIPE_API_KEY: " + stripeConfig.getApiKey()); 
        System.out.println("TEST_PAYMENT_TOKEN: " + stripeConfig.getTestPaymentToken());
        Stripe.apiKey = stripeConfig.getApiKey(); 

    }

    /* 
    private StripeConfig stripeConfig;
    
    @Autowired
    public StripeUserService(StripeConfig stripeConfig) {
        this.stripeConfig = stripeConfig;
        Stripe.apiKey = stripeConfig.getApiKey();
        System.out.println("STRIPE_API_KEY: " + stripeConfig.getApiKey());
        System.out.println("TEST_PAYMENT_TOKEN: " + stripeConfig.getTestPaymentToken());
        
    }*/


    public Customer createStripeCustomer(String email, String name) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(email)
                .setName(name)
                .build();

        Customer customer = Customer.create(params);
        System.out.println("Cliente creado en Stripe: " + customer.getId());
        return customer;
    }

    public PaymentMethod createPaymentMethod() throws StripeException {
        PaymentMethodCreateParams.Token token =
                PaymentMethodCreateParams.Token.builder()
                        .setToken(stripeConfig.getTestPaymentToken())
                        .build();

        PaymentMethodCreateParams params =
                PaymentMethodCreateParams.builder()
                        .setType(PaymentMethodCreateParams.Type.CARD)
                        .setCard(token)
                        .build();

        PaymentMethod paymentMethod = PaymentMethod.create(params);
        System.out.println("Payment method creado: " + paymentMethod.getId());
        return paymentMethod;
    }

    public void attachPaymentMethodToCustomer(String paymentMethodId, String customerId) throws StripeException {
        PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);

        PaymentMethodAttachParams attachParams =
                PaymentMethodAttachParams.builder()
                        .setCustomer(customerId)
                        .build();

        paymentMethod.attach(attachParams);
        System.out.println("Payment method asociado al cliente.");
    }

    public Product createStripeProduct(String name, String description) throws StripeException {
        ProductCreateParams productParams =
                ProductCreateParams.builder()
                        .setName(name)
                        .setDescription(description)
                        .build();
        Product product = Product.create(productParams);
        System.out.println("Producto creado en Stripe: " + product.getId());
        return product;
    }

    public Price createStripePrice(String productId, Long amount, String currency) throws StripeException {
        PriceCreateParams params =
                PriceCreateParams
                        .builder()
                        .setProduct(productId)
                        .setCurrency(currency)
                        .setUnitAmount(amount)
                        .build();
        Price price = Price.create(params);
        System.out.println("Precio creado en Stripe: " + price.getId());
        return price;
    }

    public PaymentIntent createPayment(String paymentMethodId, Price price, String email) throws StripeException {

        String customerId = findCustomerByEmail(email);
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(price.getUnitAmount())
                        .setCurrency(price.getCurrency())
                        .setPaymentMethod(paymentMethodId)
                        .setConfirm(true)
                        .setCustomer(customerId)
                        .addPaymentMethodType("card")
                        .putMetadata("product_id", price.getProduct())
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        System.out.println("Pago creado: " + paymentIntent.getId());
        return paymentIntent;
    }


    private String findCustomerByEmail(String email) throws StripeException {
        CustomerListParams params = CustomerListParams.builder().setEmail(email).setLimit(1L).build();
        List<Customer> customers = Customer.list(params).getData();
        if (!customers.isEmpty()) {
            return customers.get(0).getId();
        }
        return null;
    }


    public String getProducts() throws StripeException {
        ProductListParams params = ProductListParams.builder()
                .setLimit(10L)
                .build();

        ProductCollection products = Product.list(params);

        System.out.println("Productos existentes en Stripe:");
        products.getData().forEach(product -> {
            System.out.println("- " + product.getName() + " (ID: " + product.getId() + ")");
        });
        return products.getData().getFirst().getId();
    }

    public Price getProductPrice(String productId) throws StripeException {
        PriceListParams params = PriceListParams.builder()
                .setProduct(productId)
                .setLimit(1L)
                .build();

        PriceCollection prices = Price.list(params);

        if (!prices.getData().isEmpty()) {
            Price price = prices.getData().get(0);
            System.out.println("Price encontrado: " + price.getId());
            return price;
        } else {
            System.out.println("No se encontró ningún precio para el producto con ID: " + productId);
            return null;
        }
    }
}
