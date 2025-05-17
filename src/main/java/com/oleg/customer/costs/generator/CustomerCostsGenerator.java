package com.oleg.customer.costs.generator;

import com.oleg.customer.costs.data.CustomerCostsData;
import com.oleg.customer.costs.mappings.CategoryMappings;
import com.oleg.customer.costs.mappings.DescriptionMappings;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CustomerCostsGenerator {

    private static final Logger logger = getLogger(CustomerCostsGenerator.class);

    private static final LocalDate END_DATE = LocalDate.of(2021, 1, 1);
    private static final LocalDate START_DATE = LocalDate.of(2020, 1, 1);

    private final CategoryMappings categoryMappings;
    private final DescriptionMappings descriptionMappings;

    public CustomerCostsGenerator(CategoryMappings categoryMappings,
                                  DescriptionMappings descriptionMappings) {
        this.categoryMappings = categoryMappings;
        this.descriptionMappings = descriptionMappings;
    }

    public List<CustomerCostsData> generateCustomerCosts(int userId) {
        List<CustomerCostsData> result = new ArrayList<>();

        LocalDate currentDate = START_DATE;
        while (!currentDate.isAfter(END_DATE)) {

            int txCount = ThreadLocalRandom.current().nextInt(1, 8);

            final LocalDate cD = currentDate;
            List<LocalDateTime> times = IntStream.range(0, txCount)
                .mapToObj(i -> randomDateTimeWithinDay(cD))
                .sorted()
                .toList();

            for (LocalDateTime dateTime : times) {
                int categoryId = randomCategoryId();
                String categoryName = categoryMappings.getCategory(categoryId).description();

                BigDecimal amount = randomAmountForCategory();
                String description = generateDescription(categoryName);

                result.add(new CustomerCostsData(
                    -1,
                    userId,
                    categoryId,
                    amount,
                    description,
                    dateTime
                ));
            }

            currentDate = currentDate.plusDays(1);
        }

        logger.info("Customer costs generated for user {}.", userId);

        return result;
    }

    private LocalDateTime randomDateTimeWithinDay(LocalDate date) {
        int hour = ThreadLocalRandom.current().nextInt(0, 24);
        int minute = ThreadLocalRandom.current().nextInt(0, 60);
        int second = ThreadLocalRandom.current().nextInt(0, 60);
        return LocalDateTime.of(date, LocalTime.of(hour, minute, second));
    }

    private int randomCategoryId() {
        return ThreadLocalRandom.current().nextInt(1, categoryMappings.size() + 1);
    }

    private BigDecimal randomAmountForCategory() {
        double min = 10.0;
        double max = 500.0;

        double value = ThreadLocalRandom.current().nextDouble(min, max);
        return BigDecimal.valueOf(Math.round(value * 100) / 100.0);
    }

    private String generateDescription(String categoryName) {
        String template = descriptionMappings.getDescription(
            ThreadLocalRandom.current().nextInt(descriptionMappings.size())
        );

        return String.format(template, categoryName);
    }
}
