package asw.sfingegram.enigmi.eventpublisher;

import asw.sfingegram.common.api.event.DomainEvent;
import asw.sfingegram.enigmi.api.event.*;
import asw.sfingegram.enigmi.domain.EnigmiEventPublisher;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Logger;

@Component 
public class EnigmiEventPublisherImpl implements EnigmiEventPublisher {

    private final Logger logger = Logger.getLogger(EnigmiEventPublisherImpl.class.toString());

    @Autowired
    private KafkaTemplate<String, DomainEvent> template;

    private String channel = EnigmiEventChannel.channel;

    @Override
    public void publish(DomainEvent event) {
        logger.info("PUBLISHING EVENT: " + event.toString() + " ON CHANNEL: " + channel);
        template.send(channel, event);
        // template.flush();
    }
}
