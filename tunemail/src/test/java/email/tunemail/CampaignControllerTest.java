package email.tunemail;

import email.tunemail.dto.CampaignDto;
import email.tunemail.model.Campaign;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignService campaignService;

    @Test
    public void testCreateCampaign_Success() throws Exception {
        // Arrange
        CampaignDto campaignDto = new CampaignDto("Test Subject", "Test Content", "test@example.com", LocalDateTime.now());
        Campaign savedCampaign = new Campaign();
        savedCampaign.setSubject("Test Subject");

        when(campaignService.createCampaign(any(CampaignDto.class))).thenReturn(savedCampaign);

        // Act and Assert
        mockMvc.perform(post("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(campaignDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subject").value("Test Subject"));
    }

    @Test
    public void testGetCampaign_Success() throws Exception {
        // Arrange
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setSubject("Test Campaign");

        when(campaignService.getCampaignById(1L)).thenReturn(campaign);

        // Act and Assert
        mockMvc.perform(get("/api/campaigns/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("Test Campaign"));
    }
}
