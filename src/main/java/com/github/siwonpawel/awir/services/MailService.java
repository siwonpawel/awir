package com.github.siwonpawel.awir.services;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.siwonpawel.awir.domain.User;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService
{

    private final JavaMailSender javaMailSender;

    public void sendMail(User user, MultipartFile file) throws Exception
    {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setFrom("kontakt@awir.pl");
        mimeMessageHelper.setReplyTo("kontakt@awir.pl");
        mimeMessageHelper.setSubject("Welcome, %s".formatted(user.getName()));
        mimeMessageHelper.setText("<html><body><p>Here is an image:</p><img src='cid:image' width='300' height='300'></body></html>", true);
        mimeMessageHelper.addInline("image", new ByteArrayResource(user.getImage()), file.getContentType());

        javaMailSender.send(message);
    }
}