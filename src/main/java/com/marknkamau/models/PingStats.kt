package com.marknkamau.models

class PingStats(val ipAddress: String, val packetsSent: String, val packetsReceived: String, val packetsLost: String,
                val packetsLostPercentage: String, val minLatency: String, val maxLatency: String, val avgLatency: String)
