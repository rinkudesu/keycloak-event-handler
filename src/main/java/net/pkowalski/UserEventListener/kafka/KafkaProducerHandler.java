package net.pkowalski.UserEventListener.kafka;

import net.pkowalski.UserEventListener.kafka.messages.UserDeletedMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerHandler {
    private boolean initialised;
    private KafkaProducer<String, String> producer;
    private String kafkaTopic;
    
    public void PublishUserDeleted(String userId) {
        if (!initialised)
            Initialise();

        var kafkaMessage = new UserDeletedMessage(userId);
        var producerRecord = new ProducerRecord<String, String>(kafkaTopic, kafkaMessage.GetJson());
        producer.send(producerRecord);
    }
    
    public void Initialise() {
        if (initialised)
            return;

        Close();
        
        var kafkaAddress = System.getenv("RINKU_KAFKA_ADDRESS");
        //todo: how do you set username and password
        var kafkaUser = System.getenv("RINKU_KAFKA_USER");
        var kafkaPassword = System.getenv("RINKU_KAFKA_PASSWORD");
        kafkaTopic = System.getenv("RINKU_KAFKA_TOPIC");
        var kafkaClientId = System.getenv("RINKU_KAFKA_CLIENT_ID");
        
        var kafkaProps = new Properties();
        kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        kafkaProps.setProperty(ProducerConfig.CLIENT_ID_CONFIG, kafkaClientId);
        kafkaProps.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProps.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProps.setProperty("sasl.mechanism", "PLAINTEXT");
        kafkaProps.setProperty("sasl.username", "");
        kafkaProps.setProperty("sasl.password", "");
        
        producer = new KafkaProducer<>(kafkaProps);
        initialised = true;
    }
    
    public void Close() {
        if (producer != null) {
            producer.flush();
            producer.close();
        }
    }
}
