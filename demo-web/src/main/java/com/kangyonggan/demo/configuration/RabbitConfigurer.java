package com.kangyonggan.demo.configuration;

import com.kangyonggan.demo.constants.RabbitQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kangyonggan
 * @since 2019-03-28
 */
@Configuration
public class RabbitConfigurer {

    @Bean
    public Queue queueDemo() {
        return new Queue(RabbitQueue.QUEUE_DEMO);
    }

}
