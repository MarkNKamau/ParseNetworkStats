package com.marknkamau;

import com.marknkamau.Exceptions.*;
import com.marknkamau.models.PingStats;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

public class Presenter {
    private String date;
    private List<String> filecontents;
    private List<Map<String, String>> mapList;

    public Presenter(String date, Path source) throws Exception {
        this.date = date;
        mapList = new ArrayList<>();
        if (validateInputFile(source))
            readFileContents(source);
    }

    private void readFileContents(Path source) throws IOException, EmptyInputFile, FileDoesNotExist {
        try {
            filecontents = FileInteraction.readFileTokens(source, "Ping statistics for ");
        } catch (NoSuchFileException e) {
            throw new FileDoesNotExist();
        }
        if (filecontents.isEmpty()) {
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

    public List<String> getTextOutput() throws IncorrectInputFormat {
        if (mapList.isEmpty()) {
            mapList = getDataMapList(filecontents);
        }
        return formatForText(mapList);
    }

    public List<String> getCSVOutput() throws IncorrectInputFormat {
        if (mapList.isEmpty()) {
            mapList = getDataMapList(filecontents);
        }
        return formatForCSV(mapList);
    }

    public List<PingStats> getJSONOutput() throws IncorrectInputFormat {
        if (mapList.isEmpty()) {
            mapList = getDataMapList(filecontents);
        }
        return formatForJSON(mapList);
    }

    public void writeToFile(Path targetFile, List<String> content, FileInteraction.WriteListener listener) throws Exception {
        FileInteraction.writeFileByLine(targetFile, content, false, listener);
    }

    private List<Map<String, String>> getDataMapList(List<String> fileContents) throws IncorrectInputFormat {
        List<Map<String, String>> output = new ArrayList<>();
        for (String s : fileContents) {
            try {
                output.add(NetStatsParser.parse(s));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IncorrectInputFormat();
            }
        }
        return output;
    }

    private List<String> formatForText(List<Map<String, String>> mapList) throws IncorrectInputFormat {
        List<String> output = new ArrayList<>();
        output.add("Date: " + date);
        for (Map<String, String> map : mapList) {
            try {
                output.add("");
                output.add("Ping statistics for: " + map.get(NetStatsParser.IP_ADDRESS));
                output.add("Minimum latency: " + map.get(NetStatsParser.MINIMUM_LATENCY));
                output.add("Maximum latency: " + map.get(NetStatsParser.MAXIMUM_LATENCY));
                output.add("Average latency: " + map.get(NetStatsParser.AVERAGE_LATENCY));
                output.add("Packets sent: " + map.get(NetStatsParser.PACKETS_SENT));
                output.add("Packets received: " + map.get(NetStatsParser.PACKETS_RECEIVED));
                output.add("Packets lost: " + map.get(NetStatsParser.PACKETS_LOST));
                output.add("Percentage packets lost: " + map.get(NetStatsParser.PACKETS_LOST_PERCENT));
            } catch (Exception e) {
                if (e instanceof ArrayIndexOutOfBoundsException) {
                    throw new IncorrectInputFormat();
                }
                throw e;
            }
        }
        return output;
    }

    private List<String> formatForCSV(List<Map<String, String>> mapList) throws IncorrectInputFormat {
        List<String> output = new ArrayList<>();
        output.add(date);
        output.add("IP ADDRESS, MIN, MAX, AVG, SENT, RECEIVED, LOST, % LOSS");
        for (Map<String, String> map : mapList) {
            try {
                output.add(map.get(NetStatsParser.IP_ADDRESS) + ", "
                        + map.get(NetStatsParser.MINIMUM_LATENCY) + ", "
                        + map.get(NetStatsParser.MAXIMUM_LATENCY) + ", "
                        + map.get(NetStatsParser.AVERAGE_LATENCY) + ", "
                        + map.get(NetStatsParser.PACKETS_SENT) + ", "
                        + map.get(NetStatsParser.PACKETS_RECEIVED) + ", "
                        + map.get(NetStatsParser.PACKETS_LOST) + ", "
                        + map.get(NetStatsParser.PACKETS_LOST_PERCENT)
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

    private List<PingStats> formatForJSON(List<Map<String, String>> mapList) throws IncorrectInputFormat {
        List<PingStats> output = new ArrayList<>();
        for (Map<String, String> map : mapList) {
            try {
                PingStats pingStats = new PingStats(
                        map.get(NetStatsParser.IP_ADDRESS),
                        map.get(NetStatsParser.PACKETS_SENT),
                        map.get(NetStatsParser.PACKETS_RECEIVED),
                        map.get(NetStatsParser.PACKETS_LOST),
                        map.get(NetStatsParser.PACKETS_LOST_PERCENT),
                        map.get(NetStatsParser.MINIMUM_LATENCY),
                        map.get(NetStatsParser.MAXIMUM_LATENCY),
                        map.get(NetStatsParser.AVERAGE_LATENCY)
                );
                output.add(pingStats);
            } catch (Exception e) {
                if (e instanceof ArrayIndexOutOfBoundsException) {
                    throw new IncorrectInputFormat();
                }
                throw e;
            }
        }
        return output;
    }

}
