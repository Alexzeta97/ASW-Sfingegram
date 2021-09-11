package asw.sfingegram.connessioni.eventpublisher;

import asw.sfingegram.common.api.event.DomainEvent;
import asw.sfingegram.connessioni.api.event.*;
import asw.sfingegram.connessioni.domain.ConnessioniEventPublisher;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Logger;

@Component 
public class ConnessioniEventPublisherImpl implements ConnessioniEventPublisher {

    private final Logger logger = Logger.getLogger(ConnessioniEventPublisherImpl.class.toString());

    @Autowired
    private KafkaTemplate<String, DomainEvent> template;

	private String channel = ConnessioniEventChannel.channel; 

    @Override
    public void publish(DomainEvent event) {
        logger.info("PUBLISHING EVENT: " + event.toString() + " ON CHANNEL: " + channel);
        template.send(channel, event);
        // template.flush();
    }
}
