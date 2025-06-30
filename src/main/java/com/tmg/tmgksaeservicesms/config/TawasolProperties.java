package com.tmg.tmgksaeservicesms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "tawasol")
public class TawasolProperties {
    private String baseUrl;
    private String username;
    private String password;
    private String senderId;
    private String messageType;
    private OtpConfig otp = new OtpConfig();

    @Data
    public static class OtpConfig {
        private int length = 6;
        private int expiryMinutes = 5;
    }
}