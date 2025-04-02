package com.playtech.util.xml.helpers;
import com.playtech.report.column.Column;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class CsvProcessor {
    public static List<Map<String, Object>> readCsv(String filePath, List<Column> inputs) {
        List<Map<String, Object>> data = new ArrayList<>();
        String line;
        List<Object[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String firstline = br.readLine();
            while ((line = br.readLine()) != null) {
                Object[] values = line.split(",");
                lines.add(values);
            }

            for (int i = 0; i < lines.size(); i++) {
                Object[] row = lines.get(i);
                if (row.length != inputs.size()) continue; // Skips faulty rows

                Map<String, Object> rowData = new HashMap<>();
                for (int j = 0; j < inputs.size(); j++) {
                    rowData.put(inputs.get(j).getName(), row[j]);
                }
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
