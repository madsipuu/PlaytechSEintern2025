package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OrderingTransformer implements Transformer {
    public final static String NAME = "Ordering";
    private final Column input;
    private final Order order;

    // TODO: Implement transformer logic
    public OrderingTransformer(Column input, Order order) {
        this.input = input;
        this.order = order;
    }

    @Override
    public void transform(Report report, List<Map<String, Object>> rows) {
        String sortby = getInput().getName();
        rows.sort(Comparator.comparing(row -> (String) row.get(sortby)));

    }

    public enum Order {
        ASC,
        DESC
    }

    public Column getInput() {
        return input;
    }

    public Order getOrder() {
        return order;
    }
}
