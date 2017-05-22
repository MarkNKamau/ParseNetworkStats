package com.marknkamau.models;

import java.util.List;

public class JSONOutput {
    private String date;
    private List<PingStats> pingStats;

    public JSONOutput(String date, List<PingStats> pingStats) {
        this.date = date;
        this.pingStats = pingStats;
    }
}
