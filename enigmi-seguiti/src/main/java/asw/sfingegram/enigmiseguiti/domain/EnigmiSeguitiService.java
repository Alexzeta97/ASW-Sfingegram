package asw.sfingegram.enigmiseguiti.domain;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.logging.Logger;

import java.util.*; 

@Service 
public class EnigmiSeguitiService {

	@Autowired 
	private EnigmiSeguitiRepository enigmiSeguitiRepository;

	private final Logger logger = Logger.getLogger(EnigmiSeguitiService.class.toString());


	/* Trova gli enigmi (in formato breve) degli utenti seguiti da utente. */ 
	public Collection<EnigmaSeguito> getEnigmiSeguiti(String utente) {

		return enigmiSeguitiRepository.findByUtente(utente);
	}


	// Crea un nuovo EnigmaSeguito.
	public EnigmaSeguito createEnigmaSeguito(
		String utente, Long idEnigma, String autoreEnigma,
		String tipoEnigma, String titoloEnigma, String[] testoEnigma
	) {
		EnigmaSeguito enigmaSeguito = new EnigmaSeguito(
			utente,
			idEnigma,
			autoreEnigma,
			tipoEnigma,
			titoloEnigma,
			testoEnigma
		);
		enigmiSeguitiRepository.save(enigmaSeguito);
		logger.info("CREATED ENIGMA SEGUITO: " + enigmaSeguito);
		return enigmaSeguito;
	}
}
