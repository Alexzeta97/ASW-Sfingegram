package asw.sfingegram.enigmiseguiti.domain;

import asw.sfingegram.common.api.event.DomainEvent;
import asw.sfingegram.enigmi.api.event.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

import java.util.*; 
import java.util.stream.*; 

@Service
public class EnigmiEventHandler {

	@Autowired
	private EnigmiService enigmiService;

	private final Logger logger = Logger.getLogger(EnigmiEventHandler.class.toString());

	public void onEvent(DomainEvent event) {
		
		// Enigma creato.
        if (event.getClass().equals(EnigmaCreatedEvent.class)) {
			EnigmaCreatedEvent ece = (EnigmaCreatedEvent) event;
			enigmaCreated(ece); 
		}
        
        else {
			logger.info("UNKNOWN EVENT: " + event);			
		}
	}
	
	private Enigma enigmaCreated(EnigmaCreatedEvent event) {
		return enigmiService.createEnigma(
			event.getId(),
			event.getAutore(),
			event.getTipo(),
			event.getTitolo(),
			event.getTesto()
		);
	}
}
