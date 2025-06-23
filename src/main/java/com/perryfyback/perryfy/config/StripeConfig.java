package com.perryfyback.perryfy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "stripe")
@Configuration("stripeProperties")
public class StripeConfig {
    
        private String apiKey;
        private String testPaymentToken;
    
        //Getters and Setters go here
        public String getApiKey() {
            return apiKey;
        }
    
        public String getTestPaymentToken() {
            return testPaymentToken;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    
        public void setTestPaymentToken(String testPaymentToken) {
            this.testPaymentToken =  testPaymentToken;
        }


}
