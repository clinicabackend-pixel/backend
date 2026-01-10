package clinica_juridica.backend.listener;

import clinica_juridica.backend.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StartupNotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupNotificationListener.class);

    @Autowired
    private EmailService emailService;

    @Value("${app.notification.recipients}")
    private String recipientsStr;

    @Value("${app.notification.subject}")
    private String subject;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        logger.info("ApplicationReadyEvent received. Starting notification process...");
        logger.info("Application started. Sending notification email...");

        if (recipientsStr == null || recipientsStr.trim().isEmpty()) {
            logger.warn("No recipients configured for startup notification.");
            return;
        }

        List<String> recipients = Arrays.stream(recipientsStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        String message = String.format("La aplicacion Backend se ha iniciado correctamente el %s.",
                LocalDateTime.now());

        emailService.sendSimpleMessage(recipients, subject, message);
    }
}
