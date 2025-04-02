package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlIDREF;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AggregatorTransformer implements Transformer {
    public static final String NAME = "Aggregator";

    private final Column groupByColumn;
    private final List<AggregateBy> aggregateColumns;

    // TODO: Implement transformer logic
    public AggregatorTransformer(Column groupByColumn, List<AggregateBy> aggregateColumns) {
        this.groupByColumn = groupByColumn;
        this.aggregateColumns = aggregateColumns;
    }

    @Override
    public void transform(Report report, List<Map<String, Object>> rows) {

        Map<Object, List<Map<String, Object>>> groupedData = rows.stream()
                .collect(Collectors.groupingBy(row -> row.get(groupByColumn))); // Change "date" to your actual key
        for (Map.Entry<Object, List<Map<String, Object>>> entry : groupedData.entrySet()) {
            Object key = entry.getKey();
            List<Map<String, Object>> groupedRows = entry.getValue();

            System.out.println("Group: " + key);
            groupedRows.forEach(System.out::println);
        }

            //enne seda  on vaja ära sorteerida kuupäevade järgi
            for(AggregateBy aggregateColumn : aggregateColumns) {
                System.out.println(aggregateColumn.getInput().getName());
                System.out.println(aggregateColumn.getOutput().getName());
                System.out.println(aggregateColumn.getMethod());
                System.out.println(" ");
            }
        //}
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class AggregateBy {
        @XmlIDREF
        private Column input;
        private Method method;
        @XmlIDREF
        private Column output;

        public Column getInput() {
            return input;
        }

        public Column getOutput() {
            return output;
        }

        public Method getMethod() {
            return method;
        }
    }

    public enum Method {
        SUM,
        AVG
    }

}
