package asw.sfingegram.enigmiseguiti.domain;


import org.springframework.data.repository.CrudRepository;

import java.util.*; 

public interface EnigmiSeguitiRepository extends CrudRepository<EnigmaSeguito, EnigmaSeguitoKey> {
	
	public Collection<EnigmaSeguito> findByUtente(String utente);
}
