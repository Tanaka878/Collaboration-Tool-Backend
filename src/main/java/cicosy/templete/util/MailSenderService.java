package cicosy.templete.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

    private final JavaMailSender mailSender;

    @Autowired
    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleMail(String to, String taskName, String projectName) {

        String subject = "Task Assignment";
        String text = "Task is: " + taskName + "\nOn Project : " + projectName
                + "\n\nBest Regards\nThe Team";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            logger.info("Email sent successfully to {}", to);
        } catch (MailException e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    public void ResetPassword(String otp, String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Reset Password OTP");
            message.setText("Otp to login and change password: " + otp);
            mailSender.send(message);
            logger.info("Reset password email sent successfully to {}", email);
        } catch (MailException e) {
            logger.error("Error sending reset password email to {}: {}", email, e.getMessage());
            throw new RuntimeException("Failed to send reset password email", e); // Re-throw if you want to propagate the error
        }
    }
}