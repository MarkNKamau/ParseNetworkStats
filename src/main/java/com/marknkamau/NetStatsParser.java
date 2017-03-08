package com.marknkamau;

import java.util.HashMap;
import java.util.Map;

class NetStatsParser {
    static final String IP_ADDRESS = "ip_address";
    static final String PACKETS_SENT = "packets_sent";
    static final String PACKETS_RECEIVED = "packets_received";
    static final String PACKETS_LOST = "packets_lost";
    static final String PACKETS_LOST_PERCENT = "packets_lost_percentage";
    static final String MINIMUM_LATENCY = "min_latency";
    static final String MAXIMUM_LATENCY = "max_latency";
    static final String AVERAGE_LATENCY = "avg_latency";

    static Map<String, String> parse(String s) throws ArrayIndexOutOfBoundsException {
        Map<String, String> parsedOutput = new HashMap<>();
        parsedOutput.put(IP_ADDRESS, s.split(":")[0].trim());

        String packets = s.split(":")[2].split("App")[0].trim();
        parsedOutput.put(PACKETS_SENT, packets.split("=")[1].split(",")[0].trim());
        parsedOutput.put(PACKETS_RECEIVED, packets.split("=")[2].split(",")[0].trim());
        String totalLost = packets.split("=")[3].trim();
        parsedOutput.put(PACKETS_LOST, totalLost.split("\\(")[0].trim());
        parsedOutput.put(PACKETS_LOST_PERCENT, totalLost.split("\\(")[1].split("%")[0].trim());

        String latency = s.split("Approximate round trip times in milli-seconds:")[1].trim();
        parsedOutput.put(MINIMUM_LATENCY, latency.split("=")[1].split("ms")[0].trim());
        parsedOutput.put(MAXIMUM_LATENCY, latency.split("=")[2].split("ms")[0].trim());
        parsedOutput.put(AVERAGE_LATENCY, latency.split("=")[3].split("ms")[0].trim());

        return parsedOutput;
    }
}
