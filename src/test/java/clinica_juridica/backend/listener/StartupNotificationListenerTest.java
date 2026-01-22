package clinica_juridica.backend.listener;

import clinica_juridica.backend.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StartupNotificationListenerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private StartupNotificationListener listener;

    @Test
    void onApplicationEvent_shouldLogMessage() {
        // When
        listener.onApplicationEvent();

        // Then
        // Since the email functionality was removed, we just verify the method runs
        // without exception.
        // We can also verify that emailService is NOT called, just to be sure.
        verify(emailService, times(0)).sendSimpleMessage(any(), any(), any());
    }
}
