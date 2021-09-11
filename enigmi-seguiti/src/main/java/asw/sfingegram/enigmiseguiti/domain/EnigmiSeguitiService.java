package asw.sfingegram.enigmiseguiti.domain;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*; 
import java.util.stream.*; 

@Service 
public class EnigmiSeguitiService {

	@Autowired 
	private EnigmiSeguitiRepository enigmiSeguitiRepository;

	/* Trova gli enigmi (in formato breve) degli utenti seguiti da utente. */ 
	public Collection<Enigma> getEnigmiSeguiti(String utente) {

		// Recupera tutta la collezione di entità EnigmaSeguito associate all'utente.
		Collection<EnigmaSeguito> enigmiSeguiti = enigmiSeguitiRepository.findByUtente(utente);

		// Converti la collezione recuperata in una lista di entità Enigma.
		Collection<Enigma> enigmi =
			enigmiSeguiti
				.stream()
				.map(
					es -> new Enigma(
						es.getIdEnigma(),
						es.getAutoreEnigma(),
						es.getTipoEnigma(),
						es.getTitoloEnigma(),
						es.getTestoEnigma()
					)
				)
				.collect(Collectors.toSet());

		return enigmi; 
	}
}
