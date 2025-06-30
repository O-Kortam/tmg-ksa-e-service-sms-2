package com.tmg.tmgksaeservicesms.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendSmsRequest {

//    @NotBlank(message = "Phone number is required")
//    @Pattern(regexp = "^\\d{1,15}$", message = "Phone number must be 1-15 digits")
    private String phoneNumber;

//    @NotBlank(message = "Message content is required")
    private String message;
}