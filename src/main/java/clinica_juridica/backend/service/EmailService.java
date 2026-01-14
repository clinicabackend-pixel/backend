package clinica_juridica.backend.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${app.sendgrid.key}")
    private String sendGridApiKey;

    @Value("${app.mail.from}")
    private String fromEmail;

    /**
     * Sends a simple email to a list of recipients using SendGrid Web API.
     *
     * @param to      List of recipient email addresses
     * @param subject Email subject
     * @param text    Email body text
     */
    @Async
    public void sendSimpleMessage(List<String> to, String subject, String text) {
        if (to == null || to.isEmpty()) {
            logger.warn("No recipients provided for email notification.");
            return;
        }

        Email from = new Email(fromEmail);
        Content content = new Content("text/plain", text);
        SendGrid sg = new SendGrid(sendGridApiKey);

        // SendGrid API allows multiple personalizations, but for a "simple message"
        // style
        // where everyone sees the same email, we can either send individual emails or
        // put all in one. To avoid exposing all emails to everyone (CC effect),
        // it's better to iterate or use BCC.
        // For simplicity and safety (privacy), let's send individually in this
        // implementation,
        // or use the Personalization object to add multiple 'To's if they are meant to
        // know each other.
        // Given the use case (notification to admins), adding all to one
        // Personalization is usually fine.

        // Let's create one Personalization with all recipients.
        Personalization personalization = new Personalization();
        for (String email : to) {
            personalization.addTo(new Email(email));
        }

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);
        mail.addPersonalization(personalization);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                logger.info("Email sent successfully to: {}", to);
            } else {
                logger.error("Failed to send email. Status Code: {}, Body: {}", response.getStatusCode(),
                        response.getBody());
            }
        } catch (IOException e) {
            logger.error("Failed to make request to SendGrid", e);
        }
    }
}
