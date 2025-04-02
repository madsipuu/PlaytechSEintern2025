package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MathOperationTransformer implements Transformer {
    public final static String NAME = "MathOperation";

    private final List<Column> inputs;
    private final MathOperation operation;
    private final Column output;

    // TODO: Implement transformer logic
    public MathOperationTransformer(List<Column> inputs, MathOperation operation, Column output) {
        this.inputs = inputs;
        this.operation = operation;
        this.output = output;
    }

    @Override
    public void transform(Report report, List<Map<String, Object>> rows) {
        for (Map<String, Object> row : rows) {
            double result = 0;

            if(operation == MathOperation.ADD) {
                for (Column column : inputs) {
                    Object value = row.get(column.getName());
                    if (value instanceof Number) {
                        result += ((Number) value).doubleValue();
                    }
                }
            }

            if(operation == MathOperation.SUBTRACT) {
                ArrayList<Double> subs = new ArrayList<>();
                for (Column column : inputs) {
                    Object value = row.get(column.getName());
                    if (value instanceof Number) {
                        subs.add(((Number) value).doubleValue());
                    }
                }
                result += subs.getFirst();
                for (int i = 1; i < subs.size(); i++) {
                    result -= subs.get(i);
                }
            }
            row.put(output.getName(), result);
        }
    }

    public enum MathOperation {
        ADD,
        SUBTRACT,
    }
}
