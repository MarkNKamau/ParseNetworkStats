package com.marknkamau

import com.google.gson.GsonBuilder
import com.marknkamau.models.JSONOutput
import com.marknkamau.utils.FileInteraction.WriteListener
import com.marknkamau.models.PingStats
import com.marknkamau.utils.FileInteraction
import com.marknkamau.utils.extractPingStats
import com.marknkamau.utils.readFileTokens
import com.marknkamau.utils.validateInputFile
import java.io.File
import java.nio.file.Path
import java.util.ArrayList

class Presenter(val date: String, source: Path, val outputFolder: String, val outputFile: String) {
    var pingStats: MutableList<PingStats>

    init {
        pingStats = mutableListOf()
        val fileContents: MutableList<String>

        if (source.validateInputFile()) {
            fileContents = source.readFileTokens("Ping statistics for ")
            pingStats = fileContents.extractPingStats()
        }
    }

    fun createTXTOutput(listener: WriteListener) {
        val target = File("$outputFolder\\$outputFile.txt").toPath()
        writeToFile(target, getTextOutput(), listener)
    }

    fun createCSVOutput(listener: WriteListener) {
        val target = File("$outputFolder\\$outputFile.csv").toPath()
        writeToFile(target, getCSVOutput(), listener)
    }

    fun createJsonOutput(listener: WriteListener) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val outputJSON = gson.toJson(JSONOutput(date, getJsonOutput()))
        val list = ArrayList<String>()
        list.add(outputJSON)

        val target = File("$outputFolder\\$outputFile.json").toPath()
        writeToFile(target, list, listener)
    }

    private fun getTextOutput(): MutableList<String> {
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

    private fun getCSVOutput(): MutableList<String> {
        val output: MutableList<String> = mutableListOf()
        output.add(date)
        output.add("IP ADDRESS, MIN, MAX, AVG, SENT, RECEIVED, LOST, % LOSS")

        pingStats.mapTo(output) {
            "${it.ipAddress}, ${it.minLatency}, ${it.maxLatency}, ${it.avgLatency}, " +
                    "${it.packetsSent}, ${it.packetsReceived}, ${it.packetsLost}, ${it.packetsLostPercentage}"
        }

        return output
    }

    private fun getJsonOutput() = pingStats

    private fun writeToFile(targetFile: Path, content: List<String>, listener: WriteListener) {
        FileInteraction.writeFileByLine(targetFile, content, false, listener)
    }

}
