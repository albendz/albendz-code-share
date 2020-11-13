package com.alicia.data

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Name(
        @Column(name = "first_name")
        val firstName: String,

        @Column(name = "last_name")
        val lastName: String
) {
    override fun toString(): String =  "$lastName, $firstName"
}