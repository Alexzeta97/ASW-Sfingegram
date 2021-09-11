package asw.sfingegram.connessioni.domain;

import asw.sfingegram.common.api.event.DomainEvent;
import asw.sfingegram.connessioni.api.event.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Logger; 
import java.util.*; 

@Service
public class ConnessioniService {

	@Autowired
	private ConnessioniConAutoriRepository connessioniConAutoriRepository;

	@Autowired
	private ConnessioniConTipiRepository connessioniConTipiRepository;

	@Autowired
	private ConnessioniEventPublisher eventPublisher;

 	public ConnessioneConAutore createConnessioneConAutore(String utente, String autore) {
		
		// Crea e salva la connessione nel database.
		ConnessioneConAutore connessione = new ConnessioneConAutore(utente, autore); 
		connessione = connessioniConAutoriRepository.save(connessione);

		// Pubblica l'evento relativo alla creazione della connessione.
		DomainEvent event = new ConnessioneConAutoreCreatedEvent(
			connessione.getId(),
			connessione.getUtente(),
			connessione.getAutore()
		);
		eventPublisher.publish(event);

		return connessione;
	}

 	public ConnessioneConTipo createConnessioneConTipo(String utente, String tipo) {
		
		// Crea e salva la connessione nel database.
		ConnessioneConTipo connessione = new ConnessioneConTipo(utente, tipo); 
		connessione = connessioniConTipiRepository.save(connessione);

		// Pubblica l'evento relativo alla creazione della connessione.
		DomainEvent event = new ConnessioneConTipoCreatedEvent(
			connessione.getId(),
			connessione.getUtente(),
			connessione.getTipo()
		);
		eventPublisher.publish(event);

		return connessione;
	}

 	public ConnessioneConAutore getConnessioneConAutore(Long id) {
		ConnessioneConAutore connessione = connessioniConAutoriRepository.findById(id).orElse(null);
		return connessione;
	}

 	public ConnessioneConTipo getConnessioneConTipo(Long id) {
		ConnessioneConTipo connessione = connessioniConTipiRepository.findById(id).orElse(null);
		return connessione;
	}

 	public Collection<ConnessioneConAutore> getConnessioniConAutori() {
		Collection<ConnessioneConAutore> connessioni = connessioniConAutoriRepository.findAll();
		return connessioni;
	}

 	public Collection<ConnessioneConTipo> getConnessioniConTipi() {
		Collection<ConnessioneConTipo> connessioni = connessioniConTipiRepository.findAll();
		return connessioni;
	}

	public Collection<ConnessioneConAutore> getConnessioniConAutoriByUtente(String utente) {
		Collection<ConnessioneConAutore> connessioni = connessioniConAutoriRepository.findByUtente(utente);
		return connessioni;
	}

	public Collection<ConnessioneConTipo> getConnessioniConTipiByUtente(String utente) {
		Collection<ConnessioneConTipo> connessioni = connessioniConTipiRepository.findByUtente(utente);
		return connessioni;
	}

}
