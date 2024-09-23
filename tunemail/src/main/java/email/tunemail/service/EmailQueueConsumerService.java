package email.tunemail.service;


import com.rabbitmq.client.AMQP;
import email.tunemail.model.Campaign;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


public class emailQueueConsumerService {

    @Autowired
    private AIemailService emailService;

    @RabbitListener(queues = "emailQueue", ackMode = "MANUAL")
    public void receiveEmail(Campaign campaign, AMQP.Channel channel, Message message) throws IOException {
        try {
            emailService.sendEmail(campaign.getRecipientList(), campaign.getSubject(), campaign.getContent());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // Implement retry logic or forward to DLQ if retries are exhausted
        if (message.getMessageProperties().getRedelivered()) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // Send to DLQ
        } else {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // Requeue
        }
    }
}
@RabbitListener(queues = "emailQueue-dlx")
public void handleFailedEmail(Campaign campaign) {
    // Handle messages that landed in the Dead-Letter Queue (e.g., log failure, notify user)
    System.out.println("Email permanently failed for campaign: " + campaign.getId());
}


