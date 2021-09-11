package asw.sfingegram.connessioni.domain;

import asw.sfingegram.common.api.event.DomainEvent; 

public interface ConnessioniEventPublisher {
    
    public void publish(DomainEvent event);
}
