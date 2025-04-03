package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;

import java.util.List;
import java.util.Map;

public class StringFormatterTransformer implements Transformer {
    public final static String NAME = "StringFormatter";
    private final List<Column> inputs;
    private final String format;
    private final Column output;

    // TODO: Implement transformer logic
    public StringFormatterTransformer(List<Column> inputs, String format, Column output) {
        this.inputs = inputs;
        this.format = format;
        this.output = output;
    }

    @Override
    public void transform(Report report, List<Map<String, Object>> rows) {

    }

    public List<Column> getInputs() {
        return inputs;
    }

    public String getFormat() {
        return format;
    }

    public Column getOutput() {
        return output;
    }
}
