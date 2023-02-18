package net.pkowalski.UserEventListener.provider;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class KeycloakUserDeletedEventListener implements EventListenerProvider {
    @Override
    public void onEvent(Event event) {
        System.out.println(event.getType().toString());
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        System.out.println(adminEvent.getOperationType().toString());
    }

    @Override
    public void close() {

    }
}
