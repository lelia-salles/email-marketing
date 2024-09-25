package email.tunemail;

import email.tunemail.service.GMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GMailServiceTest {

    @Autowired
    private GMailService gmailService;

    @Test
    public void testSendEmail() {
        gmailService.sendEmail("recipient@example.com", "Test Subject", "This is a test email.");
    }
}
