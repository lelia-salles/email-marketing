package email.tunemail.controller;

import email.tunemail.dto.CampaignDto;
import email.tunemail.model.Campaign;
import email.tunemail.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Manages creation, scheduling, and sending of e-mail campaigns
@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@RequestBody CampaignDto campaignDto) {
        Campaign campaign = campaignService.createCampaign(campaignDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(campaign);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.getCampaignById(id));
    }

}
