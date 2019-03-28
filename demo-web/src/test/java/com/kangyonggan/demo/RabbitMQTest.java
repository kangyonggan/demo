package com.kangyonggan.demo;

import com.kangyonggan.demo.constants.RabbitQueue;
import com.kangyonggan.demo.model.User;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author kangyonggan
 * @since 2019-03-28
 */
public class RabbitMQTest extends AbstractTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void convertAndSend() throws Exception {
        User user = new User();
        user.setEmail("admin@kangyonggan.com");
        user.setCreatedTime(new Date());
        rabbitTemplate.convertAndSend(RabbitQueue.QUEUE_DEMO, user);

        System.in.read();
    }

}
