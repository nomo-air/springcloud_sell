package com.imooc.order.message.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqReceiver {

    @RabbitListener(bindings = @QueueBinding(
            key = "fruit",
            value = @Queue("fruitQueue"),
            exchange = @Exchange("myOrder")
    ))
    public void process(String message) {
        log.info("MqReceiver: {}", message);
    }
}
