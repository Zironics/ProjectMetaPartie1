package com.example.interfacemeta;

import java.io.*;
import java.util.stream.Stream;

public class csv_handler {

    public int countExistingCSVFiles(String csvFileNamePrefix) {
        File directory = new File("./Examples");
        int count = 0;
        FilenameFilter filter = (dir, name) -> name.startsWith(csvFileNamePrefix) && name.endsWith(".csv");
        File[] files = directory.listFiles(filter);
        if (files != null) {
            count = files.length;
        }
        return count;
    }

    public void saveMkspToCSV(MKSP mksp) throws IOException {

        int file_id = countExistingCSVFiles("test") + 1;

        String filePath = "./Examples/test" + file_id + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            // Nb_sacs, Nb_objets
            writer.append(String.valueOf(mksp.getSacksCount()));
            writer.append(",");
            writer.append(String.valueOf(mksp.getObjectsCount()));
            writer.newLine();

            // poids des sacs

            for(int i = 0; i < mksp.getSacksCount(); i++) {
                writer.append(String.valueOf(mksp.getSackWeight(i)));
                if(i != mksp.getSacksCount() - 1)   writer.append(",");
            }

            writer.newLine();

            // poids des objets

            for(int i = 0; i < mksp.getObjectsCount(); i++) {
                writer.append(String.valueOf(mksp.getObjectWeight(i)));
                if(i != mksp.getObjectsCount() - 1)   writer.append(",");
            }

            writer.newLine();

            // valeurs des objets

            for(int i = 0; i < mksp.getObjectsCount(); i++) {
                writer.append(String.valueOf(mksp.getObjectValue(i)));
                if(i != mksp.getObjectsCount() - 1)   writer.append(",");
            }
        }
    }
    public MKSP getMkspFromCSV(String filePath) throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = reader.readLine();

            /*int [] nb = Stream.of(line.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();*/

            line = reader.readLine();
            int [] sacks_weights = Stream.of(line.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            line = reader.readLine();
            int [] objects_weights = Stream.of(line.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            line = reader.readLine();
            int [] objects_values = Stream.of(line.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();

           MKSP mksp = new MKSP(sacks_weights,objects_weights,objects_values);

           return mksp;

        }
    }

}
