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
	private EnigmiRepository enigmiRepository;

	@Autowired
	private ConnessioniConAutoriRepository connessioniConAutoriRepository;

    @Autowired
	private ConnessioniConTipiRepository connessioniConTipiRepository;

	@Autowired
	private EnigmiSeguitiRepository enigmiSeguitiRepository;

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
		updateEnigmiSeguitiConnAutore(connessione.getUtente(), connessione.getAutore());
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
		updateEnigmiSeguitiConnTipo(connessione.getUtente(), connessione.getTipo());
		return connessione;
	}


	// Aggiorna la collezione di entità EnigmiSeguiti in seguito all'aggiunta di
	// una nuova connessione utente-autore.
	private void updateEnigmiSeguitiConnAutore(String utente, String autore) {
		addEnigmiSeguitiUtente(utente, enigmiRepository.findByAutore(autore));
	}


	// Aggiorna la collezione di entità EnigmiSeguiti in seguito all'aggiunta di
	// una nuova connessione utente-tipo.
	private void updateEnigmiSeguitiConnTipo(String utente, String tipo) {
		addEnigmiSeguitiUtente(utente, enigmiRepository.findByTipo(tipo));
	}


	// Aggiunge nella base di dati un'entità EnigmaSeguito per ogni entità Enigma
	// seguita da un certo utente.
	private void addEnigmiSeguitiUtente(String utente, Collection<Enigma> enigmi) {
		
		for (Enigma enigma : enigmi) {

			EnigmaSeguito enigmaSeguito = new EnigmaSeguito(
				utente,
				enigma.getId(),
				enigma.getAutore(),
				enigma.getTipo(),
				enigma.getTitolo(),
				enigma.getTesto()
			);
			enigmiSeguitiRepository.save(enigmaSeguito);
			logger.info("CREATED ENIGMA SEGUITO: " + enigmaSeguito);
		}
	}
}
