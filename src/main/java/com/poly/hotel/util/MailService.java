/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 *
 * @author PHUONG LAM
 */
public class MailService {
    
    private static String sentCode;

    public static void sendEmail(String to, String from, String username, String password, String content) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.socketFactory.port", "587");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, "Quản lý khách sạn"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Đặt lại mật khẩu");
            message.setText(content);
            Transport.send(message);
            System.out.println("Send email success to " + to);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String resetCode() {
        sentCode = String.format("%06d" ,(Math.random() * 1000000));
        return sentCode;
    }
    
    public static void sendEmailResetPassword(String email) {
        String from = "quanlykhachsan@gmail.com";
        String username = "";
        String password = "";
        String content = "Mã đặt lại mật khẩu : " + resetCode();
        sendEmail(email, from, username, password, content);
    }
    
    public static boolean verifyCode(String input) {
        return sentCode.equals(input);
    }
}
