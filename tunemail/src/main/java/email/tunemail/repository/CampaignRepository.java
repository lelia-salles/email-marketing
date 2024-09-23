package email.tunemail.repository;

import email.tunemail.model.Campaign;

import java.time.LocalDateTime;
import java.util.List;

public class CampaignRepository {

    public Campaign save(Campaign campaign) {
        return campaign;
    }

    public <T> ScopedValue<T> findById(Long campaignId) {
        return null; //review return type
    }

    public List<Campaign> findAllByScheduledTimeBeforeAndSentFalse(LocalDateTime now) {
        return null; // review return type
    }


}
