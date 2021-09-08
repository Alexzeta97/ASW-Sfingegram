package asw.sfingegram.enigmi.api.event;

import asw.sfingegram.common.api.event.DomainEvent; 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Evento di dominio relativo alla creazione di un nuovo enigma.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnigmaCreatedEvent implements DomainEvent {

	private Long id;
	private String autore;
	private String tipo;
	private String titolo;
	private String[] testo;
}
