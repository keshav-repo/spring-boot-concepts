package org.example.Jobs;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;

public class ProduceStockvalues {

    /*
    1. Read data from all files from a directory and create object of Stock and Stock value
    2. Produce the stock value
     */
    public static void main(String[] args) {
        try{
            File file = new File("/Users/keshavkumar/learn24/stock-market/script"+File.separator+"output_data.csv");
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {

                }
            
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
