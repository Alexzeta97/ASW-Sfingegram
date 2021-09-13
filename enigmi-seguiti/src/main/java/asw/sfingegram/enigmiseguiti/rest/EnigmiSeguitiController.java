package asw.sfingegram.enigmiseguiti.rest;

import asw.sfingegram.enigmiseguiti.domain.*; 

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant; 
import java.time.Duration; 

import java.util.logging.Logger; 
import java.util.*; 
import java.util.stream.*;

@RestController
public class EnigmiSeguitiController {

	private final Logger logger = Logger.getLogger(EnigmiSeguitiController.class.toString()); 

	@Autowired 
	private EnigmiSeguitiService enigmiSeguitiService;

	/* Trova gli enigmi (in formato breve) degli utenti seguiti da utente. */ 
	@GetMapping("/enigmiseguiti/{utente}")
	public Collection<Enigma> getEnigmiSeguiti(@PathVariable String utente) {
		Instant start = Instant.now();
		logger.info("REST CALL: getEnigmiSeguiti " + utente); 

		// Recupera tutta la collezione di entità EnigmaSeguito associate all'utente.
		Collection<EnigmaSeguito> enigmiSeguiti = enigmiSeguitiService.getEnigmiSeguiti(utente);

		// Converti la collezione recuperata in un insieme di entità Enigma.
		Collection<Enigma> enigmi = enigmiSeguitiToEnigmi(enigmiSeguiti);

		Duration duration = Duration.between(start, Instant.now()); 
		logger.info("getEnigmiSeguiti " + utente + " (trovati " + enigmi.size() + " enigmi in " + duration.toMillis() + " ms): " + enigmi);
		return enigmi; 
	}


	// Converti una collezione di entità EnigmaSeguito in una collezione di entità Enigma.
	private Collection<Enigma> enigmiSeguitiToEnigmi(Collection<EnigmaSeguito> enigmiSeguiti) {
		return
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
	}
}
