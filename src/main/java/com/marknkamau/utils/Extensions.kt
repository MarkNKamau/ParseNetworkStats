package com.marknkamau.utils

import com.marknkamau.Exceptions.*
import com.marknkamau.models.PingStats
import java.nio.file.Path

fun Path.validateInputFile(): Boolean {
    try {
        val inputExtension = this.fileName.toString().split(".")[1]
        if (inputExtension == "txt") return true else throw IncorrectInputExtension()
    } catch (e: Exception) {
        if (e is ArrayIndexOutOfBoundsException) throw InputFileMissingExtension() else throw e
    }
}

fun Path.readFileTokens(delimiter: String): MutableList<String> {
    val content: MutableList<String>
    try {
        content = FileInteraction.readFileTokens(this, delimiter)
    } catch (e: NoSuchFileException) {
        throw FileDoesNotExist()
    }
    if (content.isEmpty()) throw EmptyInputFile()
    return content
}

fun MutableList<String>.extractPingStats(): MutableList<PingStats> {
    val content: MutableList<PingStats> = mutableListOf()
    for (s in this) {
        try {
            content.add(NetStatsParser.parse(s))
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw IncorrectInputFormat()
        }
    }
    return content
}