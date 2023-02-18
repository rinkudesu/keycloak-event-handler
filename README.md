This is a plugin for Keycloak that allows pushing certain user-related events to Kafka.
That way, without any API communication, any action made by a user (or administrator) can be then propagated to the rest of the system.

In order to load this into Keycloak you need to:
1. add the jar file to `/opt/keycloak/providers` (when running Keycloak in Docker),
2. add `--spi-event-listener-user-deleted-event-listener-enabled=true --spi-event-listener-user-deleted-event-listener=user-deleted-event-listener` to Keycloak startup parameters.

Then, you'll be able to go to Configure > Realm settings > Events and select "user-deleted-event-listener" as an event listener for your current realm.

Note that, right now, this listener is only handling user deletions, as it's the only action required by the rest of rinkudesu.
Since other microservices don't need to be aware of other user-related actions (including user creation), no other actions will probably ever be handled here.