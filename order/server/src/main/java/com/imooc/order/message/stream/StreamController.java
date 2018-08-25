package com.imooc.order.message.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;

@RestController
public class StreamController {

    @Autowired
    private Processor source;

    @GetMapping("/sendMessage")
    public void sendMessage() {
        String message = "now" + new Date();
        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(message);
        Message<String> build = stringMessageBuilder.build();
        source.output().send(build);
    }
}
