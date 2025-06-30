package com.tmg.tmgksaeservicesms.service;
import com.tmg.tmgksaeservicesms.config.TawasolProperties;
import com.tmg.tmgksaeservicesms.dto.request.SendOtpRequest;
import com.tmg.tmgksaeservicesms.dto.request.SendSmsRequest;
import com.tmg.tmgksaeservicesms.dto.request.VerifyOtpRequest;
import com.tmg.tmgksaeservicesms.dto.response.OtpResponse;
import com.tmg.tmgksaeservicesms.dto.response.SmsResponse;
import com.tmg.tmgksaeservicesms.dto.response.VerifyOtpResponse;
//import com.tmg.tmgksaeservicesms.entity.OtpEntity;
import com.tmg.tmgksaeservicesms.exception.TawasolApiException;
//import com.tmg.tmgksaeservicesms.repository.OtpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class OtpService {

//    private final OtpRepository otpRepository;
    private final SmsService smsService;
    private final TawasolProperties tawasolProperties;
    private final Random random = new Random();

    public OtpService( SmsService smsService, TawasolProperties tawasolProperties) {
//        this.otpRepository = otpRepository;
        this.smsService = smsService;
        this.tawasolProperties = tawasolProperties;
    }

    public OtpResponse generateAndSendOtp(SendOtpRequest request) {
        // Format phone number
        String phoneNumber = formatPhoneNumber(request.getPhoneNumber());

        // Generate OTP
        String otpCode = generateOtpCode(tawasolProperties.getOtp().getLength());

        // Calculate expiry time
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(tawasolProperties.getOtp().getExpiryMinutes());

        // Save OTP to database
//        OtpEntity otpEntity = saveOtp(phoneNumber, otpCode, expiryTime);

        // Send OTP via SMS
        String otpMessage = String.format("Your verification code is: %s. It will expire in %d minutes.",
                otpCode, tawasolProperties.getOtp().getExpiryMinutes());

        SendSmsRequest smsRequest = SendSmsRequest.builder()
                .phoneNumber(phoneNumber)
                .message(otpMessage)
                .build();

        SmsResponse smsResponse = smsService.sendSms(smsRequest);

        // Create and return response
        return OtpResponse.builder()
                .messageId(smsResponse.getMessageId())
                .status(smsResponse.getStatus())
                .phoneNumber(phoneNumber)
                .expiryTime(expiryTime.format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
        String phoneNumber = formatPhoneNumber(request.getPhoneNumber());

        // Find OTP record for the phone number
//        Optional<OtpEntity> otpEntityOpt = otpRepository.findByPhoneNumber(phoneNumber);

//        if (otpEntityOpt.isEmpty()) {
//            throw new TawasolApiException("No OTP found for the provided phone number");
//        }

//        OtpEntity otpEntity = otpEntityOpt.get();

        // Check if OTP is already verified
//        if (otpEntity.isVerified()) {
//            throw new TawasolApiException("OTP already verified");
//        }

        // Check if OTP is expired
//        if (LocalDateTime.now().isAfter(otpEntity.getExpiresAt())) {
//            throw new TawasolApiException("OTP has expired");
//        }
//
//        // Verify OTP code
//        if (!otpEntity.getOtpCode().equals(request.getOtpCode())) {
//            throw new TawasolApiException("Invalid OTP code");
//        }

        // Mark OTP as verified
//        otpEntity.setVerified(true);
//        otpRepository.save(otpEntity);

        // Return success response
        return VerifyOtpResponse.builder()
                .verified(true)
                .phoneNumber(phoneNumber)
                .build();
    }

    private String generateOtpCode(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

//    private OtpEntity saveOtp(String phoneNumber, String otpCode, LocalDateTime expiryTime) {
//        // Check if OTP record already exists for the phone number
////        Optional<OtpEntity> existingOtp = otpRepository.findByPhoneNumber(phoneNumber);
//
//        OtpEntity otpEntity;
//        if (existingOtp.isPresent()) {
//            // Update existing record
//            otpEntity = existingOtp.get();
//            otpEntity.setOtpCode(otpCode);
//            otpEntity.setExpiresAt(expiryTime);
//            otpEntity.setVerified(false);
//        } else {
//            // Create new record
//            otpEntity = OtpEntity.builder()
//                    .phoneNumber(phoneNumber)
//                    .otpCode(otpCode)
//                    .expiresAt(expiryTime)
//                    .verified(false)
//                    .build();
//        }
//
//        return otpRepository.save(otpEntity);
//    }

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