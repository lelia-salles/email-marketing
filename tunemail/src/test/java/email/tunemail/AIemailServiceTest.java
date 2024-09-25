package email.tunemail;

import email.tunemail.service.AIemailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIemailServiceTest {

    @Autowired
    private AIemailService aiemailService;

    @Test
    public void testSendHTMLEmail() throws MessagingException {
        aiemailService.sendEmail("recipient@example.com", "Test Subject", "This is a test email with HTML content.");
    }
}
