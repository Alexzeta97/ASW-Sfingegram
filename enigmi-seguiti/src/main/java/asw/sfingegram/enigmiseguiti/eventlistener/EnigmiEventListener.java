package asw.sfingegram.enigmiseguiti.eventlistener;

import asw.sfingegram.common.api.event.DomainEvent;
import asw.sfingegram.enigmi.api.event.*;
import asw.sfingegram.enigmiseguiti.domain.*; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Logger;


// Componente che intercetta gli eventi di dominio emessi dal servizio "enigmi".
@Component 
public class EnigmiEventListener {
 
    private final Logger logger = Logger.getLogger(EnigmiEventListener.class.toString());

    @Autowired
    private EnigmiEventHandler handler;

	@KafkaListener(topics = EnigmiEventChannel.channel)
    public void listen(ConsumerRecord<String, DomainEvent> record) throws Exception {
        logger.info("ENIGMI EVENT LISTENER: " + record.toString());
        DomainEvent event = record.value();
		handler.onEvent(event); 
    }
}
