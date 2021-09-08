package asw.sfingegram.connessioni.api.event;

import asw.sfingegram.common.api.event.DomainEvent; 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Evento di dominio relativo alla creazione di una nuova connessione
// utente-tipo.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnessioneConTipoCreatedEvent implements DomainEvent {

	private Long id; 
	private String utente; 
	private String tipo; 
}
