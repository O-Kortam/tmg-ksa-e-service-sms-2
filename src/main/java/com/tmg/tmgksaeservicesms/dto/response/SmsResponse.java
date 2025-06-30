package com.tmg.tmgksaeservicesms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsResponse {
    private String messageId;
    private String status;
    private String phoneNumber;
    private String message;
}