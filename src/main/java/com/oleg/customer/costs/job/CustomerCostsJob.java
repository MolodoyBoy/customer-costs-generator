package com.oleg.customer.costs.job;

import com.oleg.customer.costs.analytics.customer_costs.command.CreateCustomerCostsCommand;
import com.oleg.customer.costs.generator.CustomerCostsGenerator;
import com.oleg.customer.costs.kafka.CustomerCostsPublisher;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static java.util.stream.IntStream.rangeClosed;

@Service
public class CustomerCostsJob {

    private static final Logger logger = getLogger(CustomerCostsJob.class);

    private static final int USER_COUNT = 30_000;

    private final CustomerCostsPublisher customerCostsPublisher;
    private final CustomerCostsGenerator customerCostsGenerator;

    public CustomerCostsJob(CustomerCostsPublisher customerCostsPublisher, CustomerCostsGenerator customerCostsGenerator) {
        this.customerCostsPublisher = customerCostsPublisher;
        this.customerCostsGenerator = customerCostsGenerator;
    }

    @PostConstruct
    public void startJob() {
        logger.info("Job started.");

        rangeClosed(1, USER_COUNT)
            .boxed()
            .forEach(userId -> {
                List<CreateCustomerCostsCommand> customerCosts = customerCostsGenerator.generateCustomerCosts(userId);
                customerCostsPublisher.publish(customerCosts);
            });

        logger.info("Job finished.");
    }
}