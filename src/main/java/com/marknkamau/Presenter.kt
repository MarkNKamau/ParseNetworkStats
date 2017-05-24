package com.marknkamau

import com.marknkamau.utils.FileInteraction.WriteListener
import com.marknkamau.models.PingStats
import com.marknkamau.utils.FileInteraction
import com.marknkamau.utils.extractPingStats
import com.marknkamau.utils.readFileTokens
import com.marknkamau.utils.validateInputFile
import java.nio.file.Path

class Presenter(val date: String, source: Path) {
    var pingStats: MutableList<PingStats>

    init {
        pingStats = mutableListOf()
        val fileContents: MutableList<String>

        if (source.validateInputFile()) {
            fileContents = source.readFileTokens("Ping statistics for ")
            pingStats = fileContents.extractPingStats()
        }
    }

    fun getTextOutput(): MutableList<String> {
        val output: MutableList<String> = mutableListOf()
        output.add("Date: $date")

        for (stats in pingStats) {
            output.add("")
            output.add("Ping statistics for: ${stats.ipAddress}")
            output.add("Minimum latency: ${stats.minLatency}")
            output.add("Maximum latency: ${stats.maxLatency}")
            output.add("Average latency: ${stats.avgLatency}")
            output.add("Packets sent: ${stats.packetsSent}")
            output.add("Packets received: ${stats.packetsReceived}")
            output.add("Packets lost: ${stats.packetsLost}")
            output.add("Percentage packets lost: ${stats.packetsLostPercentage}")
        }

        return output
    }

    fun getCSVOutput(): MutableList<String> {
        val output: MutableList<String> = mutableListOf()
        output.add(date)
        output.add("IP ADDRESS, MIN, MAX, AVG, SENT, RECEIVED, LOST, % LOSS")

        for (stats in pingStats) {
            output.add("" +
                    "${stats.ipAddress}, " +
                    "${stats.minLatency}, " +
                    "${stats.maxLatency}, " +
                    "${stats.avgLatency}, " +
                    "${stats.packetsSent}, " +
                    "${stats.packetsReceived}, " +
                    "${stats.packetsLost}, " +
                    stats.packetsLostPercentage)
        }

        return output
    }

    fun getJsonOutput() = pingStats

    fun writeToFile(targetFile:Path, content: List<String>, listener: WriteListener){
        FileInteraction.writeFileByLine(targetFile, content, false, listener)
    }

}
