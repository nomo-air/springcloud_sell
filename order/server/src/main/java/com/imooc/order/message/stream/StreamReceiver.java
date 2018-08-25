package com.imooc.order.message.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Processor.class)
@Slf4j
public class StreamReceiver {

    @StreamListener(Sink.INPUT)
    public void process(String message) {
        log.info("StreamReceiver: {}", message);
    }

}
