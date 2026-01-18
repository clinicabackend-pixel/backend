package clinica_juridica.backend.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${app.sendgrid.key}")
    private String sendGridApiKey;

    @Value("${app.mail.from}")
    private String fromEmail;

    /**
     * Sends an email to a list of recipients using SendGrid Web API.
     *
     * @param to            List of recipient email addresses
     * @param subject       Email subject
     * @param contentString Email body text or HTML
     * @param isHtml        Enable HTML content
     * @param inlineImage   Optional: Attach an inline image (Base64 string +
     *                      Content-ID). Can be null.
     */
    @Async
    public void sendSimpleMessage(List<String> to, String subject, String contentString, boolean isHtml,
            Attachments inlineImage) {
        if (to == null || to.isEmpty()) {
            logger.warn("No recipients provided for email notification.");
            return;
        }

        // --- TEMPORARY LOGGING FOR LOCAL DEVELOPMENT ---
        logger.info("=================================================");
        logger.info("EMAIL MOCKED DETECTED - SENDING TO LOGS");
        logger.info("TO: {}", to);
        logger.info("SUBJECT: {}", subject);
        logger.info("MESSAGE (preview):\n{}", contentString.substring(0, Math.min(contentString.length(), 200)));
        logger.info("=================================================");
        // -----------------------------------------------

        Email from = new Email(fromEmail);
        String mimeType = isHtml ? "text/html" : "text/plain";
        Content content = new Content(mimeType, contentString);
        SendGrid sg = new SendGrid(sendGridApiKey);

        Personalization personalization = new Personalization();
        for (String email : to) {
            personalization.addTo(new Email(email));
        }

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);
        mail.addPersonalization(personalization);

        if (inlineImage != null) {
            mail.addAttachments(inlineImage);
        }

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

    /* Overload for backward compatibility */
    public void sendSimpleMessage(List<String> to, String subject, String text) {
        sendSimpleMessage(to, subject, text, false, null);
    }

    public void sendSimpleMessage(List<String> to, String subject, String contentString, boolean isHtml) {
        sendSimpleMessage(to, subject, contentString, isHtml, null);
    }

    private String getLogoBase64() {
        try {
            org.springframework.core.io.Resource resource = new org.springframework.core.io.ClassPathResource(
                    "logo.png");
            if (resource.exists()) {
                try (InputStream is = resource.getInputStream()) {
                    byte[] imageBytes = is.readAllBytes();
                    return Base64.getEncoder().encodeToString(imageBytes);
                }
            }
        } catch (Exception e) {
            logger.error("Error reading logo.png from resources", e);
        }
        return null;
    }

    @Async
    public void sendInvitationEmail(String to, String token) {
        String setupUrl = "http://localhost:5173/setup-password?token=" + token;
        String subject = "Bienvenido a Clinica Juridica - Configura tu Contraseña";

        String logoHtml = "<h1>Clínica Jurídica</h1>"; // Fallback
        Attachments logoAttachment = null;

        String base64Logo = getLogoBase64();
        if (base64Logo != null) {
            // Use CID (Content-ID) reference
            logoHtml = "<img src=\"cid:logo_img\" alt=\"Clínica Jurídica\" style=\"max-height: 80px; margin-bottom: 10px; display: block; margin-left: auto; margin-right: auto;\">";

            logoAttachment = new Attachments();
            logoAttachment.setContent(base64Logo);
            logoAttachment.setType("image/png");
            logoAttachment.setFilename("logo.png");
            logoAttachment.setDisposition("inline");
            logoAttachment.setContentId("logo_img");
        }

        // HTML Template with System Colors (Red-900: #7f1d1d, Gray-700: #374151)
        String htmlMessage = String.format(
                """
                        <!DOCTYPE html>
                        <html>
                        <head>
                            <meta charset="UTF-8">
                            <style>
                                body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f3f4f6; margin: 0; padding: 0; }
                                .wrapper { padding: 20px; text-align: center; }
                                .container { display: inline-block; width: 100%%; max-width: 600px; background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05); text-align: left; }
                                .header { background-color: #7f1d1d; color: #ffffff; padding: 30px 20px; text-align: center; }
                                .header h1 { margin: 0; font-size: 24px; font-weight: 600; }
                                .content { padding: 40px 30px; color: #374151; font-size: 16px; line-height: 1.6; }
                                .button-container { text-align: center; margin-top: 30px; margin-bottom: 20px; }
                                .button { background-color: #374151; color: #ffffff !important; padding: 14px 28px; text-decoration: none; border-radius: 9999px; font-weight: 600; font-size: 16px; display: inline-block; transition: background-color 0.3s; }
                                .button:hover { background-color: #1f2937; }
                                .footer { background-color: #f9fafb; color: #9ca3af; text-align: center; padding: 20px; font-size: 13px; border-top: 1px solid #e5e7eb; }
                                .footer p { margin: 5px 0; }
                            </style>
                        </head>
                        <body>
                            <div class="wrapper">
                                <div class="container">
                                    <div class="header">
                                        %s
                                    </div>
                                    <div class="content">
                                        <p><strong>¡Hola!</strong></p>
                                        <p>Te damos la bienvenida al <strong>Sistema de Clínica Jurídica</strong>. Tu cuenta ha sido creada exitosamente.</p>
                                        <p>Para comenzar, por favor configura tu contraseña segura haciendo clic en el siguiente botón:</p>

                                        <div class="button-container">
                                            <a href="%s" class="button">Configurar Contraseña</a>
                                        </div>

                                        <p style="font-size: 14px; margin-top: 30px; color: #6b7280;">Si el botón no funciona, puedes copiar y pegar el siguiente enlace en tu navegador:</p>
                                        <p style="font-size: 12px; color: #6b7280; word-break: break-all;">%s</p>
                                    </div>
                                    <div class="footer">
                                        <p>&copy; 2026 Clínica Jurídica. Todos los derechos reservados.</p>
                                        <p>Este es un mensaje automático, por favor no respondas a este correo.</p>
                                    </div>
                                </div>
                            </div>
                        </body>
                        </html>
                        """,
                logoHtml, setupUrl, setupUrl);

        sendSimpleMessage(List.of(to), subject, htmlMessage, true, logoAttachment);
    }
}
