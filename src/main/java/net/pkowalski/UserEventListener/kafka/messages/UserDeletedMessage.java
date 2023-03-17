package net.pkowalski.UserEventListener.kafka.messages;

public class UserDeletedMessage {
    private final String _userId;
    
    public UserDeletedMessage(String userId) {
        _userId = userId;
    }
    
    // I know that this can be done in a nicer way that's not just manually writing a json, but I don't care. Too much effort at this point.
    public String GetJson() {
        return "{\"user_id\":\"" + _userId + "\"}";
    }
}
