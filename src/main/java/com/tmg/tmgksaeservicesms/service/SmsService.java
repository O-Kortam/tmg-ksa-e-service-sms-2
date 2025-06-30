package com.tmg.tmgksaeservicesms.service;

import com.tmg.tmgksaeservicesms.client.TawasolClient;
import com.tmg.tmgksaeservicesms.config.TawasolProperties;
import com.tmg.tmgksaeservicesms.dto.request.SendSmsRequest;
import com.tmg.tmgksaeservicesms.dto.response.SmsResponse;
import com.tmg.tmgksaeservicesms.exception.TawasolApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final TawasolClient tawasolClient;
    private final TawasolProperties tawasolProperties;

    public SmsResponse sendSms(SendSmsRequest request) {
        try {
            // Ensure phone number has international format (adding country code if needed)
            String phoneNumber = formatPhoneNumber(request.getPhoneNumber());

            // Call Tawasol API to send SMS
            String response = tawasolClient.sendSms(
                    "TMGSaudi",
                    "UQ:@CXv9u",
                    "TMGSA",
                    phoneNumber,
                    tawasolProperties.getMessageType(),
                    request.getMessage()
            );

            System.out.println("Tawasol SMS API response: {}"+ response);

            // Check if the response contains an error
            if (response.startsWith("ERROR")) {
                throw new TawasolApiException("Failed to send SMS: " + response);
            }

            // The response contains the message ID if successful
            return SmsResponse.builder()
                    .messageId(response)
                    .status("SENT")
                    .phoneNumber(phoneNumber)
                    .message(request.getMessage())
                    .build();

        } catch (Exception e) {
            System.out.println("Error sending SMS"+ e);
            throw new TawasolApiException("Failed to send SMS: " + e.getMessage(), e);
        }
    }

    public String checkMessageStatus(String messageId) {
        try {
            String response = tawasolClient.checkMessageStatus(messageId);
            System.out.println("Message status response: {}"+ response);
            return response;
        } catch (Exception e) {
            System.out.println("Error checking message status"+ e);
            throw new TawasolApiException("Failed to check message status: " + e.getMessage(), e);
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        // If phone number doesn't start with country code, add it (assuming Saudi Arabia as default)
        if (!phoneNumber.startsWith("966")) {
            // Remove leading zero if present
            if (phoneNumber.startsWith("0")) {
                phoneNumber = phoneNumber.substring(1);
            }
            // Add Saudi Arabia country code
            phoneNumber = "966" + phoneNumber;
        }
        return phoneNumber;
    }
}