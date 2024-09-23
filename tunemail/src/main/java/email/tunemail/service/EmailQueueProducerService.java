package email.tunemail.service;

// Handles sending email messages to RabbitMQ queues for async processing

import email.tunemail.model.Campaign;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailQueueProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEmailToQueue(Campaign campaign) {
        rabbitTemplate.convertAndSend("emailExchange", "emailRoutingKey", campaign);
    }
}
