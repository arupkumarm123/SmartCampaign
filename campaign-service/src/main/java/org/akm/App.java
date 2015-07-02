package org.akm;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.akm.model.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.kafka.Kafka;
import org.springframework.integration.dsl.kafka.KafkaHighLevelConsumerMessageSourceSpec;
import org.springframework.integration.dsl.kafka.KafkaProducerMessageHandlerSpec;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.kafka.support.ZookeeperConnect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Hello world!
 *
 */

@EnableAutoConfiguration
@EnableMongoRepositories
@SpringBootApplication
@Configuration
@EnableWebMvc
@EnableIntegration
@IntegrationComponentScan
public class App  
{
	
	public static final String TEST_TOPIC_ID = "event-stream";

	  @Component
	  public static class KafkaConfig {

	    @Value("${kafka.topic:" + TEST_TOPIC_ID + "}")
	    private String topic;

	    @Value("${kafka.address:172.17.8.101:9092}")
	    private String brokerAddress;

	    @Value("${zookeeper.address:172.17.8.101:2181}")
	    private String zookeeperAddress;

	    KafkaConfig() {
	    }

	    public KafkaConfig(String t, String b, String zk) {
	        this.topic = t;
	        this.brokerAddress = b;
	        this.zookeeperAddress = zk;
	    }

	    public String getTopic() {
	        return topic;
	    }

	    public String getBrokerAddress() {
	        return brokerAddress;
	    }

	    public String getZookeeperAddress() {
	        return zookeeperAddress;
	    }
	}
	
	  
	  @Configuration
	  public static class ProducerConfiguration {

	    @Autowired
	    private KafkaConfig kafkaConfig;

	    private static final String OUTBOUND_ID = "outbound";

	    private Log log = LogFactory.getLog(getClass());

	    @Bean(name = OUTBOUND_ID)
	    IntegrationFlow producer() {

	      log.info("starting producer flow..");
	      return flowDefinition -> {

	        Consumer<KafkaProducerMessageHandlerSpec.ProducerMetadataSpec> spec =
	          (KafkaProducerMessageHandlerSpec.ProducerMetadataSpec metadata)->
	            metadata.async(true)
	              .batchNumMessages(10)
	              .valueClassType(String.class)
	              .<String>valueEncoder(String::getBytes);

	        KafkaProducerMessageHandlerSpec messageHandlerSpec =
	          Kafka.outboundChannelAdapter(
	               props -> props.put("queue.buffering.max.ms", "15000"))
	            .messageKey(m -> m.getHeaders().get(IntegrationMessageHeaderAccessor.SEQUENCE_NUMBER))
	            .addProducer(this.kafkaConfig.getTopic(), 
	                this.kafkaConfig.getBrokerAddress(), spec);
	        flowDefinition
	            .handle(messageHandlerSpec);
	      };
	    }
	  }
	  
	  @MessagingGateway
      public interface  Sender {

          @Gateway(requestChannel = "outbound.input")
          void send(final Campaign campaign);
     }

	  @Configuration
	  public static class ConsumerConfiguration {

	    @Autowired
	    private KafkaConfig kafkaConfig;

	    private Log log = LogFactory.getLog(getClass());

	    @Bean
	    IntegrationFlow consumer() {

	      log.info("starting consumer..");

	      KafkaHighLevelConsumerMessageSourceSpec messageSourceSpec = Kafka.inboundChannelAdapter(
	          new ZookeeperConnect(this.kafkaConfig.getZookeeperAddress()))
	            .consumerProperties(props ->
	                props.put("auto.offset.reset", "smallest")
	                     .put("auto.commit.interval.ms", "100"))
	            .addConsumer("myGroup", metadata -> metadata.consumerTimeout(100)
	              .topicStreamMap(m -> m.put(this.kafkaConfig.getTopic(), 1))
	              .maxMessages(10)
	              .valueDecoder(String::new));

	      Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer = e -> e.poller(p -> p.fixedDelay(100));

	      return IntegrationFlows
	        .from(messageSourceSpec, endpointConfigurer)
	        .<Map<String, List<String>>>handle((payload, headers) -> {
	            payload.entrySet().forEach(e -> log.info(e.getKey() + '=' + e.getValue()));
	            return null;
	        })
	        .get();
	    }
	  }

    public static void main( String[] args ) {
       SpringApplication.run(App.class, args);
    }
}
