package com.marknkamau.Exceptions

class InputFileMissingExtension : BaseException(InputFileMissingExtension.TITLE, InputFileMissingExtension.MISSING_INPUT_EXTENSION) {
    companion object {
        private val TITLE = "File missing extension"
        private val MISSING_INPUT_EXTENSION = "Input file does not have an extension."
    }
}
