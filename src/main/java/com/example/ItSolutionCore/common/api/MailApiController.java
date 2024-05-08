package com.example.ItSolutionCore.common.api;

import com.example.ItSolutionCore.common.dto.MailDto;
import com.example.ItSolutionCore.common.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class MailApiController {
    private final MailService mailService;

    @PostMapping("/send")
    public String sendSimpleMail(@RequestBody MailDto mailDto){
        List<String> receivers = new ArrayList<String>();
        receivers.add(mailDto.getTo());
        mailService.sendDirectMail(mailDto, receivers);
        return "Email successfully sent to "+ mailDto.getTo();
    }

}
