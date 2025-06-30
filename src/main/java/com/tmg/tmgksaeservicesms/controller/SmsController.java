package com.tmg.tmgksaeservicesms.controller;

import com.tmg.tmgksaeservicesms.dto.request.SendSmsRequest;
import com.tmg.tmgksaeservicesms.dto.response.ApiResponse;
import com.tmg.tmgksaeservicesms.dto.response.SmsResponse;
import com.tmg.tmgksaeservicesms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
@Slf4j
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<SmsResponse>> sendSms( @RequestBody SendSmsRequest request) {
        System.out.println("Sending SMS to {}"+ request.getPhoneNumber());
        SmsResponse response = smsService.sendSms(request);
        return ResponseEntity.ok(ApiResponse.success("SMS sent successfully", response));
    }

    @GetMapping("/status/{messageId}")
    public ResponseEntity<ApiResponse<String>> checkMessageStatus(@PathVariable String messageId) {
        System.out.println("Checking status for message ID: {}"+ messageId);
        String status = smsService.checkMessageStatus(messageId);
        return ResponseEntity.ok(ApiResponse.success("Status retrieved successfully", status));
    }
}