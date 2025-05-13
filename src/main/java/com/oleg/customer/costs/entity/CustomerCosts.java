package com.oleg.customer.costs.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CustomerCosts(
        int userId,
        int categoryId,
        BigDecimal amount,
        String description,
        LocalDateTime createdAt
) {}