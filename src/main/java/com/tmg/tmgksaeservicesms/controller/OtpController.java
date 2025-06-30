package com.tmg.tmgksaeservicesms.controller;

import com.tmg.tmgksaeservicesms.dto.request.SendOtpRequest;
import com.tmg.tmgksaeservicesms.dto.request.VerifyOtpRequest;
import com.tmg.tmgksaeservicesms.dto.response.ApiResponse;
import com.tmg.tmgksaeservicesms.dto.response.OtpResponse;
import com.tmg.tmgksaeservicesms.dto.response.VerifyOtpResponse;
import com.tmg.tmgksaeservicesms.service.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/otp")
@Slf4j
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }


    @PostMapping("/send")
    public ResponseEntity<ApiResponse<OtpResponse>> sendOtp(@RequestBody SendOtpRequest request) {
//        System.out.println("Generating and sending OTP to {}" + request.getPhoneNumber());
        OtpResponse response = otpService.generateAndSendOtp(request);
        return ResponseEntity.ok(ApiResponse.success("OTP sent successfully", response));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<VerifyOtpResponse>> verifyOtp(@RequestBody VerifyOtpRequest request) {
//        System.out.println("Verifying OTP for {}"+ request.getPhoneNumber());
        VerifyOtpResponse response = otpService.verifyOtp(request);
        return ResponseEntity.ok(ApiResponse.success("OTP verified successfully", response));
    }
}