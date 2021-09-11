package asw.sfingegram.enigmiseguiti.domain;

import asw.sfingegram.common.api.event.DomainEvent;
import asw.sfingegram.connessioni.api.event.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service
public class ConnessioniEventHandler {

	@Autowired
	private ConnessioniConAutoriRepository connessioniConAutoriRepository;

    @Autowired
	private ConnessioniConTipiRepository connessioniConTipiRepository;

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
		ConnessioneConAutore connessione = new ConnessioneConAutore(
			event.getId(),
            event.getUtente(),
			event.getAutore()
		);
		connessione = connessioniConAutoriRepository.save(connessione);
		logger.info("CREATED CONNESSIONE UTENTE-AUTORE: " + connessione);
		return connessione;
	}

    private ConnessioneConTipo connTipoCreated(ConnessioneConTipoCreatedEvent event) {
		ConnessioneConTipo connessione = new ConnessioneConTipo(
			event.getId(),
            event.getUtente(),
			event.getTipo()
		);
		connessione = connessioniConTipiRepository.save(connessione);
		logger.info("CREATED CONNESSIONE UTENTE-TIPO: " + connessione);
		return connessione;
	}
}
