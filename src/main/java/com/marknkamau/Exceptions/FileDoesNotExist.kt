package com.marknkamau.Exceptions

class FileDoesNotExist : BaseException(FileDoesNotExist.TITLE, FileDoesNotExist.DOES_NOT_EXIST) {
    companion object {
        private val TITLE = "File does not exist"
        private val DOES_NOT_EXIST = "Input file does not exist."
    }
}
