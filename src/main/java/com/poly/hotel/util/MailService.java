/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.util;

import com.poly.hotel.hotelmanager.MailConstant;
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

    public static void sendEmail(String to, String from, String subject, String username, String password, String content) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
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
            message.setFrom(new InternetAddress(from, "Hotel Manager"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject, "UTF-8");
            message.setContent(content, "text/html; charset=UTF-8");
            Transport.send(message);
            System.out.println("Send email success to " + to);
            MsgBox.alertSuccess("Mã xác nhận đã gửi đến email của bạn");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String resetCode() {
        int code = (int) (Math.random() * 1000000);  // ép kiểu double → int
        sentCode = String.format("%06d", code);     // định dạng 6 chữ số, thêm số 0 phía trước nếu cần
        return sentCode;
    }

    public static void sendEmailResetPassword(String email) {
        String from = "quanlykhachsan@gmail.com";
        String subject = "Đặt lại mật khẩu";
        String username = MailConstant.email;
        String password = MailConstant.password;
        String content = "Mã đặt lại mật khẩu : " + "<strong>" + resetCode() + "</strong>";
        sendEmail(email, from, subject, username, password, content);
    }

    public static boolean verifyCode(String input) {
        return sentCode.equals(input);
    }
}
