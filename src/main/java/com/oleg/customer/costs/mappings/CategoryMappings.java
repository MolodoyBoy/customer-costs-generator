package com.oleg.customer.costs.mappings;

import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import com.oleg.customer.costs.data.CostsCategoryData;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;

@Component
public class CategoryMappings {

    private final Random rand;
    public final Map<Integer, CostsCategoryData> categoryMap;

    public CategoryMappings() {
        this.rand = new Random();
        this.categoryMap = Stream.of(
            new CostsCategoryData(1, "Translations"),
            new CostsCategoryData(2, "Online stores"),
            new CostsCategoryData(3, "Payments to the budget"),
            new CostsCategoryData(4, "Taxi"),
            new CostsCategoryData(5, "Beauty"),
            new CostsCategoryData(6, "Restaurants, cafes, bars"),
            new CostsCategoryData(7, "Education"),
            new CostsCategoryData(8, "Medical services"),
            new CostsCategoryData(9, "Supermarkets and products"),
            new CostsCategoryData(10, "Pharmacies")
        ).collect(toMap(CostsCategoryData::id, identity()));
    }

    public int size() {
        return categoryMap.size();
    }

    public CostsCategoryData getCategory(int id) {
        return categoryMap.get(id);
    }
}