package asw.sfingegram.enigmiseguiti.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.*; 

public interface ConnessioniConTipiRepository extends CrudRepository<ConnessioneConTipo, Long> {
	
	public Collection<ConnessioneConTipo> findByUtente(String utente);
}
