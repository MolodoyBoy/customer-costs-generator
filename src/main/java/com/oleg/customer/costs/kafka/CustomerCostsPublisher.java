package com.oleg.customer.costs.kafka;

import com.oleg.customer.costs.analytics.customer_costs.command.CreateCustomerCostsCommand;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CustomerCostsPublisher {

    private static final Logger logger = getLogger(CustomerCostsPublisher.class);

    private final String topic;
    private final KafkaTemplate<Integer, CreateCustomerCostsCommand> kafkaTemplate;

    public CustomerCostsPublisher(KafkaTemplate<Integer, CreateCustomerCostsCommand> kafkaTemplate,
                                  @Value("${oleg.kafka.customer-costs.topic}") String topic) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(List<CreateCustomerCostsCommand> customerCosts) {
        logger.info("Customer costs publishing started.");

        customerCosts.forEach(customerCost ->
            kafkaTemplate.send(topic, customerCost.userId(), customerCost)
        );

        logger.info("Customer costs publishing finished.");
    }
}