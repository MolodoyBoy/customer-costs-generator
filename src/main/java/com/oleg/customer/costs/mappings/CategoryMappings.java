package com.oleg.customer.costs.mappings;

import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import com.oleg.customer.costs.data.CostsCategory;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;

@Component
public class CategoryMappings {

    private final Random rand;
    public final Map<Integer, CostsCategory> categoryMap;

    public CategoryMappings() {
        this.rand = new Random();
        this.categoryMap = Stream.of(
            new CostsCategory(1, "Translations"),
            new CostsCategory(2, "Online stores"),
            new CostsCategory(3, "Payments to the budget"),
            new CostsCategory(4, "Taxi"),
            new CostsCategory(5, "Beauty"),
            new CostsCategory(6, "Restaurants, cafes, bars"),
            new CostsCategory(7, "Education"),
            new CostsCategory(8, "Medical services"),
            new CostsCategory(9, "Supermarkets and products"),
            new CostsCategory(10, "Pharmacies")
        ).collect(toMap(CostsCategory::id, identity()));
    }

    public int size() {
        return categoryMap.size();
    }

    public CostsCategory getCategory(int id) {
        return categoryMap.get(id);
    }
}