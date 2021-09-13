package asw.sfingegram.enigmiseguiti.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*; 

public interface ConnessioniConTipiRepository extends CrudRepository<ConnessioneConTipo, Long> {
	
	// Cerca tutte le connessioni utente-tipo in cui il tipo costituisce la parte
	// iniziale di una certa stringa.
	@Query("select c from ConnessioneConTipo c where locate(c.tipo, ?1) = 1")
	public Collection<ConnessioneConTipo> findByTipoIsPrefixOf(String string);
}
