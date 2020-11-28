package com.alicia.model

import com.alicia.data.Author
import java.util.*

data class AddAuthorRequest (
        val firstName: String?,
        val lastName: String?,
        val dob: Long?,
        val dod: Long? = null,
        val biography: String? = null,
): ValidatableRequest() {
    fun toAuthor(): Author =
        Author(
            firstName = firstName,
            lastName = lastName,
            birthDate = dob?.let {  Date(dob) },
            deathDate = dod?.let {  Date(dod) },
            biography = biography?.toByteArray(),
        )

    override fun getErrors(): List<String> {
        val errors = mutableListOf<String>()

        if(firstName.isNullOrEmpty()) {
            errors.add("Author first name")
        }

        if(lastName.isNullOrEmpty()) {
            errors.add("Author last name")
        }

        return errors
    }
}
