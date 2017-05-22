package com.marknkamau;

import com.marknkamau.Exceptions.*;
import com.marknkamau.models.PingStats;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

class Presenter {
    private final String date;
    private List<String> fileContents;
    private List<PingStats> pingStats;

    Presenter(String date, Path source) throws Exception {
        this.date = date;
        pingStats = new ArrayList<>();
        if (validateInputFile(source))
            readFileContents(source);
    }

    private void readFileContents(Path source) throws IOException, EmptyInputFile, FileDoesNotExist {
        try {
            fileContents = FileInteraction.readFileTokens(source, "Ping statistics for ");
        } catch (NoSuchFileException e) {
            throw new FileDoesNotExist();
        }
        if (fileContents.isEmpty()) {
            throw new EmptyInputFile();
        }
    }

    private boolean validateInputFile(Path source) throws IncorrectInputExtension, InputFileMissingExtension {
        String inputExtension;
        try {
            inputExtension = source.getFileName().toString().split("\\.")[1];

            if (!inputExtension.equals("txt")) {
                throw new IncorrectInputExtension();
            } else {
                return true;
            }
        } catch (Exception e) {
            if (e instanceof ArrayIndexOutOfBoundsException) {
                throw new InputFileMissingExtension();
            }
            throw e;
        }
    }

    List<String> getTextOutput() throws IncorrectInputFormat {
        if (pingStats.isEmpty()) {
            pingStats = getDataMapList(fileContents);
        }

        List<String> output = new ArrayList<>();
        output.add("Date: " + date);
        for (PingStats stats : pingStats) {
            try {
                output.add("");
                output.add("Ping statistics for: " + stats.getIpAddress());
                output.add("Minimum latency: " + stats.getMinLatency());
                output.add("Maximum latency: " + stats.getMaxLatency());
                output.add("Average latency: " + stats.getAvgLatency());
                output.add("Packets sent: " + stats.getPacketsSent());
                output.add("Packets received: " + stats.getPacketsReceived());
                output.add("Packets lost: " + stats.getPacketsLost());
                output.add("Percentage packets lost: " + stats.getPacketsLostPercentage());
            } catch (Exception e) {
                if (e instanceof ArrayIndexOutOfBoundsException) {
                    throw new IncorrectInputFormat();
                }
                throw e;
            }
        }
        return output;
    }

    List<String> getCSVOutput() throws IncorrectInputFormat {
        if (pingStats.isEmpty()) {
            pingStats = getDataMapList(fileContents);
        }

        List<String> output = new ArrayList<>();
        output.add(date);
        output.add("IP ADDRESS, MIN, MAX, AVG, SENT, RECEIVED, LOST, % LOSS");
        for (PingStats stats : pingStats) {
            try {
                output.add(stats.getIpAddress() + ", "
                        + stats.getMinLatency() + ", "
                        + stats.getMaxLatency() + ", "
                        + stats.getAvgLatency() + ", "
                        + stats.getPacketsSent() + ", "
                        + stats.getPacketsReceived() + ", "
                        + stats.getPacketsLost() + ", "
                        + stats.getPacketsLostPercentage()
                );
            } catch (Exception e) {
                if (e instanceof ArrayIndexOutOfBoundsException) {
                    throw new IncorrectInputFormat();
                }
                throw e;
            }
        }

        return output;
    }

    List<PingStats> getJSONOutput() throws IncorrectInputFormat {
        if (pingStats.isEmpty()) {
            pingStats = getDataMapList(fileContents);
        }
        return pingStats;
    }

    void writeToFile(Path targetFile, List<String> content, FileInteraction.WriteListener listener) throws Exception {
        FileInteraction.writeFileByLine(targetFile, content, false, listener);
    }

    private List<PingStats> getDataMapList(List<String> fileContents) throws IncorrectInputFormat {
        List<PingStats> stats = new ArrayList<>();
        for (String s : fileContents) {
            try {
                stats.add(NetStatsParser.parse(s));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IncorrectInputFormat();
            }
        }
        return stats;
    }

}
