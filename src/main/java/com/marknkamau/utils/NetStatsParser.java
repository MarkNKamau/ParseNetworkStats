package com.marknkamau.utils;

import com.marknkamau.models.PingStats;

class NetStatsParser {

    static PingStats parse(String s) throws ArrayIndexOutOfBoundsException {
        String ipAddress = s.split(":")[0].trim();

        String packets = s.split(":")[2].split("App")[0].trim();
        String packetsSent = packets.split("=")[1].split(",")[0].trim();
        String packetsReceived = packets.split("=")[2].split(",")[0].trim();

        String totalLost = packets.split("=")[3].trim();
        String packetsLost = totalLost.split("\\(")[0].trim();
        String packetsLostPercentage = totalLost.split("\\(")[1].split("%")[0].trim();

        String latency = s.split("Approximate round trip times in milli-seconds:")[1].trim();
        String minLatency = latency.split("=")[1].split("ms")[0].trim();
        String maxLatency = latency.split("=")[2].split("ms")[0].trim();
        String avgLatency = latency.split("=")[3].split("ms")[0].trim();

        return new PingStats(
                ipAddress,
                packetsSent,
                packetsReceived,
                packetsLost,
                packetsLostPercentage,
                minLatency,
                maxLatency,
                avgLatency
        );
    }
}
