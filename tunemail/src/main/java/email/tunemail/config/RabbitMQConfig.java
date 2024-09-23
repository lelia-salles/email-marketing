package email.tunemail.config;

// Configuration for RabbitMQ, queues, exchanges and bindings

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable("emailQueue")
                .withArgument("x-dead-letter-exchange", "emailExchange-dlx")
                .withArgument("x-dead-letter-routing-key", "emailRoutingKey-dlx")
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue("emailQueue-dlx", true);
    }

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange("emailExchange");
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("emailExchange-dlx");
}
    @Bean
    public Binding binding (Queue emailQueue, DirectExchange emailExchange){
        return BindingBuilder.bind(emailQueue).to(emailExchange).with("emailRoutingKey");
    }

    @Bean
    public Binding dlqBinding (Queue deadLetterQueue, DirectExchange deadLetterExchange){
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("emailRoutingKey-dlx");

    }
}

