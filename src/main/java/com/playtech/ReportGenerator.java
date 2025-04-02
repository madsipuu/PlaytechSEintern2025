package com.playtech;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;
import com.playtech.util.xml.XmlParser;
import com.playtech.util.xml.adapters.TransformerAdapter;
import com.playtech.util.xml.helpers.CsvProcessor;
import com.playtech.util.xml.helpers.TransformerWrapper;
import jakarta.xml.bind.JAXBException;

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
            System.out.println(csvData.size());
            System.out.println(csvData.get(16));
            System.out.println(csvData.get(116).keySet());
            System.out.println(csvData.get(1116).get("BetAmount"));

            //All outputs

            for(Transformer c: report.getTransformers()){
                System.out.println(c);
                c.transform(report, csvData);
            }

            
            
            








/*
            System.out.println(csvDataFilePath);
            System.out.println(reportXmlFilePath);
            System.out.println(outputDirectoryPath);

            System.out.println(report.getReportName());
            for(Column c: report.getInputs()){
                System.out.println(c.getName());
            }

            System.out.println();
            System.out.println(report.getOutputFormat());
            for(Column c: report.getOutputs()){
                System.out.println(c.getName());

            }*/


        } catch (JAXBException e) {
            System.err.println("Parsing of the xml file failed:");
            throw new RuntimeException(e);
        }


        // TODO: Implement logic
    }


}



