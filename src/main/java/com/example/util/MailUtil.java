package com.example.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;

public class MailUtil implements Serializable {
    private static final String USERNAME = "sashka.ivaschenko@gmail.com";
    private static final String PASSWORD = "QAZ12ass";

    private static Properties propertiesConfig() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.transport.protocol", "smtp");
        return properties;
    }
    public static void sendMail(String toMail, String subject, String message) {
        propertiesConfig();
        Session session = Session.getInstance(propertiesConfig(), new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        try {
            Message mailMessage = new MimeMessage(session);
            mailMessage.setFrom(new InternetAddress(USERNAME));
            mailMessage.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toMail)
            );
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            Transport.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
