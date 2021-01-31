package com.alicia.model

import com.alicia.data.Member

data class AddMemberRequest(
        val email: String,
        val firstName: String,
        val lastName: String,
) : ValidatableRequest() {

    companion object {
        const val EMAIL_ADDRESS =
                """[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}"""
    }

    override fun getErrors(): List<String> {
        val errors = mutableListOf<String>()
        if (firstName.isBlank()) {
            errors.add("First Name")
        }

        if (lastName.isBlank()) {
            errors.add("Last Name")
        }

        if (!EMAIL_ADDRESS.toRegex().matches(email)) {
            errors.add("Email")
       }

        return errors
    }

    fun toMember(): Member = Member(firstName = firstName, lastName = lastName, email = email)
}
