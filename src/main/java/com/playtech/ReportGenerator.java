package com.playtech;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;
import com.playtech.util.xml.XmlParser;
import com.playtech.util.xml.adapters.TransformerAdapter;
import com.playtech.util.xml.helpers.CsvProcessor;
import com.playtech.util.xml.helpers.TransformerWrapper;
import jakarta.xml.bind.JAXBException;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ReportGenerator {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Application should have 3 paths as arguments: csv file path, xml file path and output directory");
            System.exit(1);
        }
        String csvDataFilePath = args[0], reportXmlFilePath = args[1], outputDirectoryPath = args[2];
        try {
            Report report = XmlParser.parseReport(reportXmlFilePath);

            //Read CSV Data
            List<Map<String, Object>> csvData = CsvProcessor.readCsv(csvDataFilePath, report.getInputs());

            //System.out.println(csvData.get(1116).get("BetAmount"));
            // "C:\Users\madispuu\IdeaProjects\PlaytechSEintern2025\input\casino_gaming_results.csv" "C:\Users\madispuu\IdeaProjects\PlaytechSEintern2025\input\DailyBetWinLossReport.xml" "C:\Users\madispuu\IdeaProjects\PlaytechSEintern2025\output\DailyBetWinLossReport.jsonl"

            //Go through every transfromer whit data
            for(Transformer c: report.getTransformers()){
                System.out.println(c);
                c.transform(report, csvData);
            }


            Report.FileFormat OutputFormat= report.getOutputFormat();
            System.out.println(OutputFormat);
            System.out.println(report);
            //format and output
            try (FileWriter writer = new FileWriter(outputDirectoryPath)) {
                for (Map<String, Object> row : csvData) {
                    writer.write(convertToJson(row) + "\n"); // Write JSON object in each line
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (JAXBException e) {
            System.err.println("Parsing of the xml file failed:");
            throw new RuntimeException(e);
        }


        // TODO: Implement logic
    }

    private static String convertToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":");

            // Handle different data types
            if (entry.getValue() instanceof Number || entry.getValue() instanceof Boolean) {
                json.append(entry.getValue());
            } else {
                json.append("\"").append(entry.getValue()).append("\"");
            }
            json.append(",");
        }
        if (json.length() > 1) json.deleteCharAt(json.length() - 1); // Remove last comma
        json.append("}");
        return json.toString();
    }

}



