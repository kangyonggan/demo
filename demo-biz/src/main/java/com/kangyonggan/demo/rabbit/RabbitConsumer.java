package com.kangyonggan.demo.rabbit;

import com.kangyonggan.demo.constants.RabbitQueue;
import com.kangyonggan.demo.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * @author kangyonggan
 * @since 2019-03-28
 */
@Component
@Log4j2
public class RabbitConsumer {

    @RabbitListener(queues = RabbitQueue.QUEUE_DEMO)
    public void process(Message message) throws Exception {
        User user = convert(message.getBody());

        log.info("mq receive:{}", user);
    }

    private <T> T convert(byte[] body) throws Exception {
        ByteArrayInputStream bodyIs = new ByteArrayInputStream(body);
        ObjectInputStream in = new ObjectInputStream(bodyIs);
        return (T) in.readObject();
    }

}
