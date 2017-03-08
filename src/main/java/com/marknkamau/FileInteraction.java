package com.marknkamau;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class FileInteraction {

    public static List<String> readFileTokens(Path source, String delimiter) throws Exception {
        List<String> tokens = new ArrayList<>();
        BufferedReader reader = Files.newBufferedReader(source);

        try (Scanner scanner = new Scanner(reader)) {
            if (delimiter != null) {
                scanner.useDelimiter(delimiter);
            }
            while (scanner.hasNext()) {
                tokens.add(scanner.next());
            }
            return tokens;
        } finally {
            reader.close();
        }
    }

    public static void writeFileByLine(Path target, List<String> content, Boolean append, WriteListener listener) throws Exception {
        FileWriter fileWriter = new FileWriter(target.toString(), append);

        try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (String s : content) {
                writer.append(s, 0, s.length());
                writer.newLine();
            }

            if (listener != null){
                listener.writeSuccessful();
            }
        } finally {
            fileWriter.close();
        }
    }

    interface WriteListener {
        void writeSuccessful();
    }
}
