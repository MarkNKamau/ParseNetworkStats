package com.marknkamau.Exceptions

class EmptyFields : BaseException(EmptyFields.TITLE, EmptyFields.EMPTY_FIELDS) {
    companion object {
        private val TITLE = "Empty fields"
        private val EMPTY_FIELDS = "All fields are required"
    }
}
