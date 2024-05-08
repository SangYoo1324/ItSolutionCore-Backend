package com.example.ItSolutionCore.common.service;

import com.example.ItSolutionCore.common.dto.MailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MailService {


    private final JavaMailSender javaMailSender;



    public void sendMail(MailDto mailDto, MultipartFile multipartFile){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
            mimeMessageHelper.setTo(mailDto.getTo());
            mimeMessageHelper.setFrom("no-reply");
            mimeMessageHelper.setSubject(mailDto.getSubject());
            mimeMessageHelper.setText(mailDto.getMessage(),true);

            // file attachment(only multipartFileList not null)
            if(multipartFile !=null){
                mimeMessageHelper.addAttachment("attachment",  multipartFile);
            }
            javaMailSender.send(mimeMessage);
            log.info("Email Successfully sent to"+mimeMessage.getReplyTo());
        }catch(MessagingException e){
            log.info("fail to send email");
            throw new RuntimeException(e);
        }
    }

    public void sendDirectMail(MailDto mailDto, List<String> receivers){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        try{




            simpleMailMessage.setTo(receivers.get(0));


            // 2. 메일 제목 설정
            simpleMailMessage.setSubject(mailDto.getSubject());

            // 3. 메일 내용 설정
            simpleMailMessage.setText(mailDto.getMessage());

            // 4. 메일 전송
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e){
            e.printStackTrace();
            log.info("direct email not sent. something went wrong");
        }

    }


}
