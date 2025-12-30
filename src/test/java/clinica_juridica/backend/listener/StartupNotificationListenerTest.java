package clinica_juridica.backend.listener;

import clinica_juridica.backend.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StartupNotificationListenerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private StartupNotificationListener listener;

    @Test
    @SuppressWarnings("null")
    void onApplicationEvent_shouldSendEmail_whenRecipientsAreConfigured() {
        // Given
        String recipients = "test1@example.com, test2@example.com";
        String subject = "Test Subject";
        ReflectionTestUtils.setField(listener, "recipientsStr", recipients);
        ReflectionTestUtils.setField(listener, "subject", subject);

        // When
        listener.onApplicationEvent();

        // Then
        verify(emailService, times(1)).sendSimpleMessage(any(), eq(subject), anyString());
    }
}
