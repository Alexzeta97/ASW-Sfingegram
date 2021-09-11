package asw.sfingegram.enigmiseguiti.eventlistener;

import asw.sfingegram.common.api.event.DomainEvent;
import asw.sfingegram.connessioni.api.event.*;
import asw.sfingegram.enigmiseguiti.domain.*; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Logger;


// Componente che intercetta gli eventi di dominio emessi dal servizio "connessioni".
@Component 
public class ConnessioniEventListener {
 
    private final Logger logger = Logger.getLogger(ConnessioniEventListener.class.toString());

    @Autowired
    private ConnessioniEventHandler handler;

	@KafkaListener(topics = ConnessioniEventChannel.channel)
    public void listen(ConsumerRecord<String, DomainEvent> record) throws Exception {
        logger.info("CONNESSIONI EVENT LISTENER: " + record.toString());
        DomainEvent event = record.value();
		handler.onEvent(event); 
    }
}
