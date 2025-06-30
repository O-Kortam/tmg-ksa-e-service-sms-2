package com.tmg.tmgksaeservicesms.client;

import com.tmg.tmgksaeservicesms.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tawasol", url = "https://api.tawasolsms.com", configuration = FeignConfig.class)
public interface TawasolClient {

    @GetMapping("/index.php")
    String sendSms(
            @RequestParam("user") String username,
            @RequestParam("pass") String password,
            @RequestParam("sid") String senderId,
            @RequestParam("mno") String phoneNumber,
            @RequestParam("type") String messageType,
            @RequestParam("text") String messageText
    );

    @GetMapping("/websmsstatus")
    String checkMessageStatus(@RequestParam("respid") String messageId);
}