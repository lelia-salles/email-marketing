package email.tunemail.service;


import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.http.HttpHeaders;
import java.util.*;


@Service
public class AIemailService {

    @Value("${openai.api-key}")
    private String apiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/completions";

    public String[] generateEmailContent(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        String promptA = prompt + " Create a Version A of this email.";
        String promptB = prompt + " Create a Version B of this email.";

        String contentA = Arrays.toString(generateEmailContent(promptA));
        String contentB = Arrays.toString(generateEmailContent(promptB));

        return new String[]{contentA, contentB};




        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Create request payload for the OpenAI API
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4"); // Use GPT-4 or other available models
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", 500);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>();
        ResponseEntity<Map> response = restTemplate.postForEntity(OPENAI_API_URL, entity, Map.class);

        // Extract generated content from the API response
        Map<String, Object> choices = (Map<String, Object>) ((List<?>) response.getBody().get("choices")).get(0);
        return choices.get("text").toString();
    }

    public String generateCampaignSettings(String prompt) {
        // Similar implementation to generate campaign settings
        return generateEmailContent(prompt);
    }

    // Method to suggest optimal sending times
    public String suggestOptimalSendTime(String audienceDetails) {
        String prompt = "Based on the target audience: " + audienceDetails +
                ", suggest the best times to send an email campaign to maximize engagement.";
        return generateEmailContent(prompt);
    }

    // Method to provide AI feedback based on past campaign performance
    public String generatePerformanceFeedback(String pastPerformanceData) {
        String prompt = "Analyze the following campaign performance data and provide feedback on how to improve future campaigns: " + pastPerformanceData;
        return generateEmailContent(prompt);
    }

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailgunemailService mailgunEmailService;

    @Value("${mailgun.api-key}")
    private String apiKey;

    @Value("${mailgun.from-email}")
    private String fromEmail;

    @SneakyThrows
    public void sendEmail(String to, String subject, String content) throws MessagingException {
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(("api:" + apiKey).getBytes());
        mailgunEmailService.sendEmail(authHeader, fromEmail, to, subject, content);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // e-mail attributes

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

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



