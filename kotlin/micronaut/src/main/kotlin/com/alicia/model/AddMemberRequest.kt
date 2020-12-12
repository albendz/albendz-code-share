package com.alicia.model

import java.util.regex.Pattern

data class AddMemberRequest(
        val email: String,
        val firstName: String,
        val lastName: String,
) : ValidatableRequest() {

    companion object {
        const val EMAIL_ADDRESS =
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
    }

    override fun getErrors(): List<String> {
        val errors = mutableListOf<String>()
        if (firstName.isBlank()) {
            errors.add("First Name")
        }

        if (lastName.isBlank()) {
            errors.add("Last Name")
        }

        // TODO
        //if (!Regex.fromLiteral(EMAIL_ADDRESS).matches(email)) {
        //    errors.add("Email")
       //}

        return errors
    }
}
