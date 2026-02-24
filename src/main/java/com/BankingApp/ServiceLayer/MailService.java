package com.BankingApp.ServiceLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("javaclass2026@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
	}

	public void sendMiniStatementMail(String toEmail, byte[] pdfBytes) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setFrom("javaclass2026@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("ZenBanking - Mini Statement");
            helper.setText(
                    "Dear Customer,\n\nPlease find attached your mini statement.\n\nRegards,\nZenBanking Team"
            );

            helper.addAttachment(
                    "MiniStatement.pdf",
                    new ByteArrayResource(pdfBytes)
            );

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


}
