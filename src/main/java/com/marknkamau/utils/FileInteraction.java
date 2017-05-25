package com.marknkamau.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileInteraction {

    public static List<String> readFileTokens(Path source, String delimiter) throws IOException {
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

            if (listener != null) {
                listener.writeSuccessful(target.getFileName().toString());
            }
        } finally {
            fileWriter.close();
        }
    }

    public interface WriteListener {
        void writeSuccessful(String file);
    }
}
