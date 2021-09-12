package asw.sfingegram.enigmiseguiti.domain;

import asw.sfingegram.common.api.event.DomainEvent;
import asw.sfingegram.connessioni.api.event.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

import java.util.*; 

@Service
public class ConnessioniEventHandler {

	@Autowired 
	private ConnessioniService connessioniService;

	private final Logger logger = Logger.getLogger(ConnessioniEventHandler.class.toString());

	public void onEvent(DomainEvent event) {
		
        // Connessione utente-autore.
        if (event.getClass().equals(ConnessioneConAutoreCreatedEvent.class)) {
			ConnessioneConAutoreCreatedEvent connAutoreEvent = (ConnessioneConAutoreCreatedEvent) event;
			connAutoreCreated(connAutoreEvent); 
		}

        // Connessione utente-tipo.
        else if (event.getClass().equals(ConnessioneConTipoCreatedEvent.class)) {
			ConnessioneConTipoCreatedEvent connTipoEvent = (ConnessioneConTipoCreatedEvent) event;
			connTipoCreated(connTipoEvent); 
		}
        
        else {
			logger.info("UNKNOWN EVENT: " + event);			
		}
	}
	
	private ConnessioneConAutore connAutoreCreated(ConnessioneConAutoreCreatedEvent event) {
		return connessioniService.createConnessioneConAutore(
			event.getId(),
            event.getUtente(),
			event.getAutore()
		);
	}

    private ConnessioneConTipo connTipoCreated(ConnessioneConTipoCreatedEvent event) {
		return connessioniService.createConnessioneConTipo(
			event.getId(),
            event.getUtente(),
			event.getTipo()
		);
	}
}
