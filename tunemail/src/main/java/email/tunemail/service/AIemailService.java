package email.tunemail.service;



import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.InputStream;


@Service
@FeignClient(name = "email-service", url = "https://email-provider.com/api")
public interface AIemailService {

    @Value("${openai.api-key}")
    String apiKey = new String();

    static final String OPENAI_API_URL = "https://api.openai.com/v1/completions";

    public String generateEmailContent(String prompt);

    public String generateCampaignSettings(String prompt);

    // Method to suggest optimal sending times

    public String suggestOptimalSendTime(String audienceDetails);

    // Method to provide AI feedback based on past campaign performance

    public default String generatePerformanceFeedback(String pastPerformanceData) {
        String prompt = STR."Analyze the following campaign performance data and provide feedback on how to improve future campaigns: \{pastPerformanceData}";
        return generateEmailContent(prompt);
    }

    @Autowired
    JavaMailSender mailSender = new JavaMailSender() {
        @Override
        public MimeMessage createMimeMessage() {
            return null;
        }

        @Override
        public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
            return null;
        }

        @Override
        public void send(MimeMessage... mimeMessages) throws MailException {

        }

        @Override
        public void send(SimpleMailMessage... simpleMessages) throws MailException {

        }
    };

    @Autowired
    TemplateEngine templateEngine = new TemplateEngine();

    @Autowired
    MailgunemailService mailgunEmailService = new MailgunemailService() {
        @Override
        public void sendEmail(String apiKey, String from, String to, String subject, String text) {

        }
    };

    @Value("${mailgun.api-key}")
    String MailgunApiKey = null;

    @Value("${mailgun.from-email}")
    String fromEmail = new String();

    // include gmail here
    @SneakyThrows

    //public void sendEmail(String to, String subject, String content) throws MessagingException;
    public default void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Set email attributes
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);

        // Process the HTML template with dynamic content
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("content", content);

        // Render the HTML email template
        String htmlContent = templateEngine.process("email-template", context);
        helper.setText(htmlContent, true); // true indicates the email contains HTML content

        mailSender.send(message);
    }

}



