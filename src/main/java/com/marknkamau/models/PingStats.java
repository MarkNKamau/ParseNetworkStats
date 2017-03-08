package com.marknkamau.models;

public class PingStats {
    String ipAddress;
    String packetsSent;
    String packetsReceived;
    String packetsLost;
    String packetsLostPercentage;
    String minLatency;
    String maxLatency;
    String avgLatency;

    public PingStats(String ipAddress, String packetsSent, String packetsReceived, String packetsLost,
                     String packetsLostPercentage, String minLatency, String maxLatency, String avgLatency) {
        this.ipAddress = ipAddress;
        this.packetsSent = packetsSent;
        this.packetsReceived = packetsReceived;
        this.packetsLost = packetsLost;
        this.packetsLostPercentage = packetsLostPercentage;
        this.minLatency = minLatency;
        this.maxLatency = maxLatency;
        this.avgLatency = avgLatency;
    }
}
