package com.testproject.blogapp.service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public boolean sendVerificationEmail(String recipientEmail, int otpCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sender);
            helper.setTo(recipientEmail);
            helper.setSubject("OTP Verification from Blog App");

            String htmlContent = "<!DOCTYPE html>"
                    + "<html lang=\"en\">"
                    + "<head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                    + "<title>OTP Verification - Blog App</title>"
                    + "</head>"
                    + "<body style=\"font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 20px;\">"
                    + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px; background-color: #ffffff; border-radius: 10px; box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);\">\n" +
                    "        <tr>\n" +
                    "            <td align=\"center\" style=\"padding: 20px;\">\n" +
                    "                <h2 style=\"margin-top: 20px; color: #333;\">Blog App</h2>\n" +
                    "                <p style=\"font-size: 16px; color: #666;\">Verify your email address to access your account.</p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td align=\"center\" style=\"padding: 20px;\">\n" +
                    "                <div style=\"background-color: #f0f0f0; padding: 15px; border-radius: 5px;\">\n" +
                    "                    <h3 style=\"color: #333;\">OTP Verification Code</h3>\n" +
                    "                    <p style=\"font-size: 18px; color: #333;\">Your OTP code is: <strong>" + otpCode+"</strong></p>\n" +
                    "                    <p style=\"font-size: 14px; color: #666;\">This OTP is valid for 15 minutes.</p>\n" +
                    "                </div>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td align=\"center\" style=\"padding: 20px;\">\n" +
                    "                <p style=\"font-size: 14px; color: #666;\">If you didn't request this verification, please ignore this email.</p>\n" +
                    "                <p style=\"font-size: 14px; color: #666;\">Thank you for using Blog App!</p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>"
                    + "</body>"
                    + "</html>";
            helper.setText(htmlContent, true);
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
