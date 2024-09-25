package email.tunemail.service;

// AI Service that segments audience based on engagement

import email.tunemail.model.Campaign;
import email.tunemail.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIAudienceSegmentationService {

    @Autowired
    private AIemailService aiEmailService;

    @Autowired
    private CampaignRepository campaignRepository;

    public String[] segmentAudience(Long campaignId) {
        Campaign campaign = (Campaign) campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        // Get engagement data (e.g., open and click rates)
        String engagementData = "Open Count: " + (campaign.getOpenCountA() + campaign.getOpenCountB()) +
                ", Click Count: " + (campaign.getClickCountA() + campaign.getClickCountB());

        // Ask AI to segment audience based on engagement levels
        String prompt = "Segment the audience into high-engagement and low-engagement groups based on the following data: "
                + engagementData;
        return AIemailService.generateEmailContent(prompt);
    }
}
