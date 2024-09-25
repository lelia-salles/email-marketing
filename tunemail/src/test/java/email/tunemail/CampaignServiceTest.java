package email.tunemail;

import email.tunemail.dto.CampaignDto;
import email.tunemail.model.Campaign;
import email.tunemail.repository.CampaignRepository;
import email.tunemail.service.CampaignService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    public void testCreateCampaign_Success() {
        // Arrange
        CampaignDto campaignDto = new CampaignDto("Test Subject", "Test Content", "test@example.com", LocalDateTime.now());
        Campaign expectedCampaign = new Campaign();
        expectedCampaign.setSubject(campaignDto.getSubject());

        when(campaignRepository.save(any(Campaign.class))).thenReturn(expectedCampaign);

        // Act
        Campaign result = campaignService.createCampaign(campaignDto);

        // Assert
        assertEquals(expectedCampaign.getSubject(), result.getSubject());
        verify(campaignRepository, times(1)).save(any(Campaign.class));
    }

    @Test
    public void testGetCampaignById_Success() {
        // Arrange
        Campaign expectedCampaign = new Campaign();
        expectedCampaign.setId(1L);
        expectedCampaign.setSubject("Test Campaign");

        when(campaignRepository.findById(1L)).thenReturn(Optional.of(expectedCampaign));

        // Act
        Campaign result = campaignService.getCampaignById(1L);

        // Assert
        assertEquals(expectedCampaign.getSubject(), result.getSubject());
        verify(campaignRepository, times(1)).findById(1L);
    }
}
