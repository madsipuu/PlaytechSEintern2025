package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlIDREF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        String groupby = getGroupByColumn().getName();
        //new map where there is {GroupbyElement, new requiered inputs}
        Map<String, List<Map<String, Object>>> newmap = new HashMap<>();
        for (Map<String, Object> row : rows) {
                //System.out.println(row.get(groupby));
                String group = (String) row.get(groupby);
                newmap.computeIfAbsent(group, k -> new ArrayList<>()).add(row);
        }
        //clearing row of old values
        rows.clear();

        //now i have a new map that is grouped by the desiered element
        //We need to get the desired SUM/AVG now of desiered field
        for (Map.Entry<String, List<Map<String, Object>>> entry : newmap.entrySet()) {
            //new row for new rows
            Map<String, Object> newrow = new HashMap<>();

            //looking at each aggrebate
            for(AggregateBy aggregateColumn : aggregateColumns) {
                String AgregateInput = (aggregateColumn.getInput().getName());
                double sum = 0;
                int elements = 0;
                //looking over each element in the group
                for (Map<String, Object> row : entry.getValue()) {
                    sum +=   Double.parseDouble((String) row.get(AgregateInput));
                    elements++;
                }

                //is SUM or AVG requeired
                if(aggregateColumn.getMethod() == Method.SUM){
                    //output field:
                    String AgregateOutput = (aggregateColumn.getOutput().getName());
                    newrow.put(AgregateOutput, sum);
                }
                if(aggregateColumn.getMethod() == Method.AVG){
                    //output field:
                    String AgregateOutput = (aggregateColumn.getOutput().getName());
                    newrow.put(AgregateOutput, sum/elements);
                }


            }
            //put all new information to original rows with addisiona field for Groupby element
            newrow.put(groupby, entry.getKey());
            rows.add(newrow);
        }

    }

    public Column getGroupByColumn() {
        return groupByColumn;
    }

    public List<AggregateBy> getAggregateColumns() {
        return aggregateColumns;
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
