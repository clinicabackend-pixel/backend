package clinica_juridica.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.List;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @PostConstruct
    public void logMailConfig() {
        logger.info("EmailService initialized with: Host={}, Port={}, Username={}", mailHost, mailPort, fromEmail);
    }

    /**
     * Sends a simple email to a list of recipients.
     *
     * @param to      List of recipient email addresses
     * @param subject Email subject
     * @param text    Email body text
     */
    @Async
    @SuppressWarnings("null")
    public void sendSimpleMessage(List<String> to, String subject, String text) {
        if (to == null || to.isEmpty()) {
            logger.warn("No recipients provided for email notification.");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setSubject(subject);
            message.setText(text);

            // Convert List<String> to String[]
            message.setTo(to.toArray(new String[0]));

            emailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send email to: " + to, e);
        }
    }
}
