package com.marknkamau.Exceptions

class IncorrectInputExtension : BaseException(IncorrectInputExtension.TITLE, IncorrectInputExtension.INPUT_EXTENSION_MESSAGE) {
    companion object {
        private val TITLE = "Incompatible input file"
        private val INPUT_EXTENSION_MESSAGE = "Input file must be a plain text file (.txt)"
    }
}
