/**
 * (C) Copyright IBM Corp. 2016,2017,2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.watsonhealth.fhir.notifications.kafka.impl;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.ibm.watsonhealth.fhir.notification.FHIRNotificationEvent;
import com.ibm.watsonhealth.fhir.notification.FHIRNotificationService;
import com.ibm.watsonhealth.fhir.notification.FHIRNotificationSubscriber;
import com.ibm.watsonhealth.fhir.notification.exception.FHIRNotificationException;
import com.ibm.watsonhealth.fhir.notification.util.FHIRNotificationUtil;

/**
 * This class implements the FHIR server notification service via a Kafka topic.
 */
public class FHIRNotificationKafkaPublisher implements FHIRNotificationSubscriber {
    private static final Logger log = Logger.getLogger(FHIRNotificationKafkaPublisher.class.getName());

    private static final String PROPNAME_TOPICNAME = "topic.name";

    private static FHIRNotificationService service = FHIRNotificationService.getInstance();

    private String topicName = null;
    private Producer<String, String> producer = null;

    private Properties kafkaProps = null;

    // "Hide" the default ctor.
    protected FHIRNotificationKafkaPublisher() {
    }

    public FHIRNotificationKafkaPublisher(Properties kafkaProps) {
        log.entering(this.getClass().getName(), "ctor");
        try {
            init(kafkaProps);
        } finally {
            log.exiting(this.getClass().getName(), "ctor");
        }
    }

    /**
     * Performs any required initialization to allow us to publish events to the topic.
     */
    private void init(Properties kafkaProps) {
        log.entering(this.getClass().getName(), "init");
        try {
            this.kafkaProps = kafkaProps;
            log.finer("Kafka publisher is configured with the following properties:\n" + this.kafkaProps.toString());
            
            // We'll hard-code some properties to ensure they are set correctly.
            this.kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            this.kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            this.kafkaProps.put(ProducerConfig.CLIENT_ID_CONFIG, "fhir-server");
            
            // Next, we'll retrieve the topic name property and then remove it from the properties object
            // as it is not an official kafka property.
            topicName = this.kafkaProps.getProperty(PROPNAME_TOPICNAME);
            if (topicName == null) {
                throw new IllegalStateException("The " + PROPNAME_TOPICNAME + " property was missing from the Kafka properties.");
            }

            String bootstrapServers = this.kafkaProps.getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG);
            if (bootstrapServers == null) {
                throw new IllegalStateException("The " + ProducerConfig.BOOTSTRAP_SERVERS_CONFIG + " property was missing from the Kafka properties.");
            }
            
            // Set up our properties for connecting to the kafka server.
            // connectionProps = new Properties();
            // connectionProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, connectionInfo);
            // connectionProps.put(ProducerConfig.ACKS_CONFIG, "1");
            // connectionProps.put(ProducerConfig.RETRIES_CONFIG, "0");
            // connectionProps.put(ProducerConfig.LINGER_MS_CONFIG, "0");
            // connectionProps.put(ProducerConfig.BATCH_SIZE_CONFIG, "16384");

            // Create our producer object to be used for publishing.
            producer = new KafkaProducer<String, String>(this.kafkaProps);

            // Register this Kafka implementation as a "subscriber" with our Notification Service.
            // This means that our "notify" method will be called when the server publishes an event.
            service.subscribe(this);
            log.info("Initialized Kafka publisher for topic '" + topicName + "' using bootstrap servers: " + bootstrapServers + ".");
        } catch (Throwable t) {
            log.log(Level.SEVERE, "Caught exception while initializing Kafka publisher: ", t);
        } finally {
            log.exiting(this.getClass().getName(), "init");
        }
    }

    /**
     * Performs any necessary "shutdown" logic to disconnect from the topic.
     */
    public void shutdown() {
        log.entering(this.getClass().getName(), "shutdown");

        try {
            log.fine("Shutting down Kafka publisher for topic: '" + topicName + "'.");
            if (producer != null) {
                producer.close();
            }
        } finally {
            log.exiting(this.getClass().getName(), "shutdown");
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ibm.watsonhealth.fhir.notification.FHIRNotificationSubscriber#notify(com.ibm.watsonhealth.fhir.notification.
     * util.FHIRNotificationEvent)
     */
    @Override
    public void notify(FHIRNotificationEvent event) throws FHIRNotificationException {
        log.entering(this.getClass().getName(), "notify");
        String topicId = "[" + this.kafkaProps.getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG) + "]/" + topicName;
        try {
            String jsonString = FHIRNotificationUtil.toJsonString(event, true);
            log.fine("Publishing kafka notification event to topic '" + topicId + "', message: " + jsonString);
            producer.send(new ProducerRecord<String, String>(topicName, jsonString));
            log.fine("message sent...");
        } catch (Throwable e) {
            String msg = "Error publishing kafka notification event to topic '" + topicId;
            log.log(Level.SEVERE, msg, e);
            throw new FHIRNotificationException(msg, e);
        } finally {
            log.exiting(this.getClass().getName(), "notify");
        }
    }
}
