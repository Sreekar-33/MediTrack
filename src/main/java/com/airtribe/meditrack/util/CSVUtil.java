package com.airtribe.meditrack.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static List<String[]> readCSV(String filePath) {

        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");

                rows.add(values);
            }

        } catch (IOException e) {

            System.out.println("Error reading CSV: " + e.getMessage());
        }

        return rows;
    }

    public static void writeCSV(String filePath, String data) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {

            bw.write(data);
            bw.newLine();

        } catch (IOException e) {

            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }
}