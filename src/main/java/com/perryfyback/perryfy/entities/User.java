package com.perryfyback.perryfy.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  user_id;

    private String name;
    private String lastname;
    private String email;
    private String password;
    private String default_address;
    private String payment_method_token;

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer userId) {
        this.user_id = userId;
    }

    public String getDefaultAddress() {
        return default_address;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.default_address = defaultAddress;
    }

    public String getPaymentMethodToken() {
        return payment_method_token;
    }

    public void setPaymentMethodToken(String paymentMethodToken) {
        this.payment_method_token = paymentMethodToken;
    }
} 