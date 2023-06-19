package com.project.loanapplicationsystem.service.emailService;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService{
   @Autowired
  private JavaMailSender javaMailSender;

    @Async
    @Override
    public void send(String toEmail, String text) {


        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setFrom("obusederek@gmail.com");
            mimeMessageHelper.setSubject("Confirm your Email Address");
            mimeMessageHelper.setText(text);
            javaMailSender.send(mimeMessage);
            log.info(text);
        } catch (MessagingException | MailException e) {
            log.info("problem1 " + e.getMessage());
            throw new RuntimeException(e);
        }


    }
}
