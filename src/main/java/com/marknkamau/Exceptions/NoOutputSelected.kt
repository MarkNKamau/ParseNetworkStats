package com.marknkamau.Exceptions

class NoOutputSelected : BaseException(NoOutputSelected.TITLE, NoOutputSelected.MESSAGE) {
    companion object {
        private val TITLE = "No output selected"
        private val MESSAGE = "At least one output is required"
    }
}
