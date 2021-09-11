package asw.sfingegram.enigmiseguiti.domain;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*; 

@Service 
public class EnigmiSeguitiService {

	@Autowired 
	private EnigmiSeguitiRepository enigmiSeguitiRepository;

	/* Trova gli enigmi (in formato breve) degli utenti seguiti da utente. */ 
	public Collection<EnigmaSeguito> getEnigmiSeguiti(String utente) {

		return enigmiSeguitiRepository.findByUtente(utente);
	}
}
