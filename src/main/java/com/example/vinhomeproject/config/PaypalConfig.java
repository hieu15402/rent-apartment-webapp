package com.example.vinhomeproject.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalConfig {

    private String clientId = "AcMwsJaEpEZjy2dNbExhC2Fo5LWaiqvTLvkDA8HDzOtMotwBu4nGgjk7ZKez4yZGpU0X-EwihbFCM0Y3";
    private String clientSecret = "EJSr4eJ9uHHYIGqE3GhKCsc42kFVUfQqRCWjcE4Jv5scapWtOdCzu7Xs6ovPX9WOTEYPRRNiReptg_H3";
    private String mode = "sandbox";

    @Bean
    public APIContext apiContext() {
        return new APIContext(clientId, clientSecret, mode);
    }

}
