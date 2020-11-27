package com.alicia.model

import com.alicia.exceptions.InvalidRequestException

abstract class ValidatableRequest {

    abstract fun getErrors(): List<String>

    fun validate() {
        getErrors().let {
            if (it.isNotEmpty()) {
                throw InvalidRequestException(it)
            }
        }
    }
}