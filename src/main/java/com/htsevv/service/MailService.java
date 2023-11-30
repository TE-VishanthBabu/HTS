package com.htsevv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${email.id}")
    private String fromEmail;

    @Autowired
    private final JavaMailSender javaMailSender;

    /**
     * Sending mail to the user.
     *
     * @param toEmail
     * @param subject
     * @param body
     * @return boolean
     */
    public Boolean sendMail(String toEmail, String subject, String body) {
        try {
            MimeMessage mime = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body,true);
            javaMailSender.send(mime);
            return true;
        }
        catch (Exception exception){
            return false;
        }
    }
}
