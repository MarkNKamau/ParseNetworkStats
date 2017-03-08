package com.marknkamau.models;

import java.util.List;

public class JSONOutput {
    String date;
    List<PingStats> pingStats;

    public JSONOutput(String date, List<PingStats> pingStats) {
        this.date = date;
        this.pingStats = pingStats;
    }
}
