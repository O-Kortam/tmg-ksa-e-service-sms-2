package com.tmg.tmgksaeservicesms.dto.request;


import lombok.Data;
@Data
public class VerifyOtpRequest {

//    @NotBlank(message = "Phone number is required")
//    @Pattern(regexp = "^\\d{1,15}$", message = "Phone number must be 1-15 digits")
    private String phoneNumber;

//    @NotBlank(message = "OTP code is required")
//    @Pattern(regexp = "^\\d{6}$", message = "OTP must be 6 digits")
    private String otpCode;
}