package com.airtribe.meditrack.util;

import java.io.*;
import java.util.logging.Logger;

public class CSVUtil {

    private static final Logger logger = Logger.getLogger(CSVUtil.class.getName());


    public static void readCSV(String filePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {

                logger.info(line);

            }

        } catch (IOException e) {

            logger.severe("Error reading CSV file: " + e.getMessage());

        }

    }

    public static void writeCSV(String filePath, String[] data) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {

            String row = String.join(",", data);

            bw.write(row);
            bw.newLine();

            logger.info("Row written to CSV: " + row);

        } catch (IOException e) {

            logger.severe("Error writing CSV file: " + e.getMessage());

        }

    }
}
