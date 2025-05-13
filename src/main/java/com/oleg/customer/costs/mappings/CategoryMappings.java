package com.oleg.customer.costs.mappings;

import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import com.oleg.customer.costs.entity.Category;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;

@Component
public class CategoryMappings {

    private final Random rand;
    public final Map<Integer, Category> categoryMap;

    public CategoryMappings() {
        this.rand = new Random();
        this.categoryMap = Stream.of(
            new Category(1, "Translations"),
            new Category(2, "Online stores"),
            new Category(3, "Payments to the budget"),
            new Category(4, "Taxi"),
            new Category(5, "Beauty"),
            new Category(6, "Restaurants, cafes, bars"),
            new Category(7, "Education"),
            new Category(8, "Medical services"),
            new Category(9, "Supermarkets and products"),
            new Category(10, "Pharmacies")
        ).collect(toMap(Category::id, identity()));
    }

    public int size() {
        return categoryMap.size();
    }

    public Category getCategory(int id) {
        return categoryMap.get(id);
    }
}