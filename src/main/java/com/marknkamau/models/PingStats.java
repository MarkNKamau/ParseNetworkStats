package com.marknkamau.models;

public class PingStats {
    private String ipAddress;
    private String packetsSent;
    private String packetsReceived;
    private String packetsLost;
    private String packetsLostPercentage;
    private String minLatency;
    private String maxLatency;
    private String avgLatency;


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

    public String getIpAddress() {
        return ipAddress;
    }

    public String getPacketsSent() {
        return packetsSent;
    }

    public String getPacketsReceived() {
        return packetsReceived;
    }

    public String getPacketsLost() {
        return packetsLost;
    }

    public String getPacketsLostPercentage() {
        return packetsLostPercentage;
    }

    public String getMinLatency() {
        return minLatency;
    }

    public String getMaxLatency() {
        return maxLatency;
    }

    public String getAvgLatency() {
        return avgLatency;
    }
}
