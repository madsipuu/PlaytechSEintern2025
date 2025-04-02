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
            System.out.println(inputs.get(0).getName());
            Object first = row.get(inputs.get(0).getName());
            double firstDouble = Double.parseDouble(first.toString());
            Object second = row.get(inputs.get(1).getName());
            double secondDouble = Double.parseDouble(second.toString());

            System.out.println(firstDouble + " " + secondDouble);


            if(operation == MathOperation.ADD) {
                row.put(output.getName(), firstDouble + secondDouble);
            }
            if(operation == MathOperation.SUBTRACT) {
                row.put(output.getName(), firstDouble - secondDouble);
            }

        }
    }

    public enum MathOperation {
        ADD,
        SUBTRACT,
    }
}
