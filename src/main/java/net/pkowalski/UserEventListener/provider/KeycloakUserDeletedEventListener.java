package net.pkowalski.UserEventListener.provider;

import net.pkowalski.UserEventListener.kafka.KafkaProducerHandler;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class KeycloakUserDeletedEventListener implements EventListenerProvider {
    
    private final KafkaProducerHandler _producer;
    
    public KeycloakUserDeletedEventListener(KafkaProducerHandler producer) {
        _producer = producer;
    }
    
    @Override
    public void onEvent(Event event) {
        //DELETE_ACCOUNT
        if (event.getType().toString().equals("DELETE_ACCOUNT")) {
            _producer.PublishUserDeleted(event.getUserId());
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        //DELETE USER
        if ((adminEvent.getOperationType().toString() + adminEvent.getResourceType().name()).equals("DELETEUSER")) {
            // the string will be users/<user id>
            var userId = adminEvent.getResourcePath().split("/")[1];
            _producer.PublishUserDeleted(userId);
        }
    }

    @Override
    public void close() {
        _producer.Close();
    }
}
