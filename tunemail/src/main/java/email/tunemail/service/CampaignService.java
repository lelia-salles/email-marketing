package email.tunemail.service;

// Contains logic for creating, schedulin, and sending e-mail campaigns with A/B testing support

import email.tunemail.dto.CampaignDto;
import email.tunemail.model.Campaign;
import email.tunemail.repository.CampaignRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private AIemailService aiemailService;

    @Autowired
    private EmailQueueProducerService emailQueueProducer;

    @Scheduled(fixedRate = 60000) // Check every minute
    public void sendScheduledCampaigns() {
        List<Campaign> campaigns = campaignRepository.findAllByScheduledTimeBeforeAndSentFalse(LocalDateTime.now());

        for (Campaign campaign : campaigns) {
            try {
                // Send email
                emailQueueProducer.sendEmailToQueue(campaign);

                // Mark the campaign as sent
                campaign.setSent(true);
                campaignRepository.save(campaign);

            } catch (MessagingException e) {
                // Log error and handle accordingly
                System.out.println("Failed to send campaign: " + campaign.getId());
            }
        }
    }

    public Campaign createCampaign(CampaignDto campaignDto) {
        Campaign campaign = new Campaign();
        campaign.setSubjectA(campaignDto.getSubject() + " [Version A]");
        campaign.setContentA(emailContent[0]);
        campaign.setSubjectB(campaignDto.getSubject() + " [Version B]");
        campaign.setContentB(emailContent[1]);
        campaign.setRecipientList(campaignDto.getRecipientList());
        campaign.setScheduledTime(campaignDto.getScheduledTime());
        campaign.setSent(false); // New field to track sent status
        return campaignRepository.save(campaign);
    }

    public void scheduleSegmentedCampaign(Long campaignId, String segmentedData) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        // Use segmented audience data to divide recipients
        String[] highEngagementGroup = segmentedData.split("High-Engagement:")[1].split("Low-Engagement:")[0].split(",");
        String[] lowEngagementGroup = segmentedData.split("Low-Engagement:")[1].split(",");

        // Send personalized email to high-engagement group
        campaign.setRecipientList(String.join(",", highEngagementGroup));
        campaign.setContent("Special content for high-engagement users.");
        emailQueueProducer.sendEmailToQueue(campaign);

        // Send different email to low-engagement group
        campaign.setRecipientList(String.join(",", lowEngagementGroup));
        campaign.setContent("Content for low-engagement users.");
        emailQueueProducer.sendEmailToQueue(campaign);

        campaign.setSent(true);
        campaignRepository.save(campaign);
    }

    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));
    }

    //Method to create and save a campaign

    // Method to send the campaign to the email queue using AI-generated content and optimal send time
    // automatically select the winning version from previous A/B test

    // Schedule A/B test campaigns by splitting the recipients list into two
    @Scheduled(fixedRate = 60000) // Check every minute
    public void scheduleABTestCampaigns() {
        List<Campaign> campaigns = campaignRepository.findAllByScheduledTimeBeforeAndSentFalse(LocalDateTime.now());

        for (Campaign campaign : campaigns) {
            String[] recipients = campaign.getRecipientList().split(",");
            int midpoint = recipients.length / 2;

            // Send Version A to half of the recipients
            String recipientListA = String.join(",", Arrays.copyOfRange(recipients, 0, midpoint));
            Campaign versionACampaign = new Campaign();
            versionACampaign.setRecipientList(recipientListA);
            versionACampaign.setSubjectA(campaign.getSubjectA());
            versionACampaign.setContentA(campaign.getContentA());
            versionACampaign.setSubjectB(campaign.getSubjectB());
            emailQueueProducer.sendEmailToQueue(versionACampaign);

            // Send Version B to the other half
            String recipientListB = String.join(",", Arrays.copyOfRange(recipients, midpoint, recipients.length));
            Campaign versionBCampaign = new Campaign();
            versionBCampaign.setRecipientList(recipientListB);
            versionBCampaign.setSubject(campaign.getSubjectB());
            versionBCampaign.setContent(campaign.getContentB());
            emailQueueProducer.sendEmailToQueue(versionBCampaign);

            // Mark campaign as sent
            campaign.setSent(true);
            campaignRepository.save(campaign);
        }
    }
}
