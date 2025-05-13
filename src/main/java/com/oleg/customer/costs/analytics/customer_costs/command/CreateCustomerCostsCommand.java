package com.oleg.customer.costs.analytics.customer_costs.command;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateCustomerCostsCommand(
    int userId,
    int categoryId,
    BigDecimal amount,
    String description,
    LocalDateTime createdAt
) {}
