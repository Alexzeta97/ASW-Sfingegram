package asw.sfingegram.enigmi.domain;

import asw.sfingegram.common.api.event.DomainEvent; 

public interface EnigmiEventPublisher {

    public void publish(DomainEvent event);
}
