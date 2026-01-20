package clinica_juridica.backend.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupNotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupNotificationListener.class);

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        logger.info("ApplicationReadyEvent received. Starting notification process...");
        logger.info("Application started successfully.");

        // Email notification disabled by user request.
        // String message = String.format("La aplicacion Backend se ha iniciado
        // correctamente el %s.", LocalDateTime.now());
        // logger.info("Backend iniciado correctamente at {}", LocalDateTime.now());
        System.out.println("----------------------------------------------------------");
        System.out.println("   La aplicacion Backend se ha iniciado correctamente     ");
        System.out.println("----------------------------------------------------------");
    }
}
