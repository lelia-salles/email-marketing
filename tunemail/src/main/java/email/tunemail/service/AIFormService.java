package email.tunemail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIFormService {

    @Autowired
    private AIemailService aiemailServicemailService;

    public String generateFormFields(String campaignGoal) {
        String prompt = "Generate a form with fields for collecting user data. "
                + "Goal: " + campaignGoal + ". "
                + "Include relevant form fields like name, email, and preferences.";

        return aiemailService.generateEmailContent(prompt);
    }
}
