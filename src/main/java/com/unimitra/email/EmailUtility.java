package com.unimitra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtility {

	@Autowired
	public JavaMailSender emailSender;

	public void sendSimpleMessage(String emailTo, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailTo);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}
}
