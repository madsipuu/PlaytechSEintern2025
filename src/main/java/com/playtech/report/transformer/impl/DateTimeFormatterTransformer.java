package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;

import java.util.List;
import java.util.Map;

public class DateTimeFormatterTransformer implements Transformer {
    public static final String NAME = "DateTimeFormatter";
    private final Column input;
    private String format;
    private final Column output;
    // TODO: Implement transformer logic
    public DateTimeFormatterTransformer(Column input, String format, Column output) {
        this.input = input;
        this.format = format;
        this.output = output;
    }

    @Override
    public void transform(Report report, List<Map<String, Object>> rows) {
        for(Map<String, Object> row : rows) {
            String result = "";
            Column input = getInput();
            String format = getFormat();

        }
    }

    public String getFormat() {
        return format;
    }

    public Column getInput() {
        return input;
    }

    public Column getOutput() {
        return output;
    }
}
