package asw.sfingegram.enigmiseguiti.domain;

import lombok.*;
import java.io.Serializable;

// Chiave composta per l'entità EnigmaSeguito.
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EnigmaSeguitoKey implements Serializable {
    
    // Id dell'utente.
    @EqualsAndHashCode.Include
    private String utente;

    // Id dell'enigma.
    @EqualsAndHashCode.Include
    private Long idEnigma;
}
