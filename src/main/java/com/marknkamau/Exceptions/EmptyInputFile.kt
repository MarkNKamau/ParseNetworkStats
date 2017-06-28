package com.marknkamau.Exceptions

class EmptyInputFile : BaseException(EmptyInputFile.TITLE, EmptyInputFile.EMPTY_INPUT_FILE) {
    companion object {
        private val TITLE = "Empty input file"
        private val EMPTY_INPUT_FILE = "Input file is empty."
    }
}
