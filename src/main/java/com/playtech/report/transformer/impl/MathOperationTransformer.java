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


            String first = getInputs().get(0).getName();
            double firstnum = (double) row.get(first);
            double secondnum = 0;

            //if there are more than 2 inputs
            for (int i = 1; i < getInputs().size(); i++) {
                String second = getInputs().get(i).getName();
                secondnum =+ (double) row.get(second);
            }
            //System.out.println(firstnum + " " + secondnum);


            if(getOperation() == MathOperation.ADD) {
                row.put(getOutput().getName(), firstnum + secondnum);
            }
            if(getOperation() == MathOperation.SUBTRACT) {
                row.put(getOutput().getName(), firstnum - secondnum);
            }
        }
    }

    public List<Column> getInputs() {
        return inputs;
    }

    public MathOperation getOperation() {
        return operation;
    }

    public Column getOutput() {
        return output;
    }

    public enum MathOperation {
        ADD,
        SUBTRACT,
    }
}
