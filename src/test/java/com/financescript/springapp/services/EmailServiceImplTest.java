package com.financescript.springapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;


class EmailServiceImplTest {

    private EmailServiceImpl emailService;

    private JavaMailSenderImpl emailSender;

    @BeforeEach
    void setUp() {
        emailSender = new JavaMailSenderImpl();
        emailSender.setHost("smtp.gmail.com");
        emailSender.setPort(587);
        emailSender.setUsername("donotreply.financescript@gmail.com");
        emailSender.setPassword("!gbahj9ASDBDjravjah08bzxkjn19-o");
        Properties props = emailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        emailService = new EmailServiceImpl(emailSender);
    }

    @Test
    public void shouldSendSingleMail() throws Exception {
        // given
        String to = "nyhochan.lee@gmail.com";
        String subject = "From FinanceScript 2.0";
        String text = "Testing EmailServiceImplTest";

        // when
        emailService.sendSimpleMessage(to, subject, text);
    }
}