package com.alicia.data

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "author")
data class Author (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var authorId: UUID? = null,

    @Column
    var firstName: String? = null,

    @Column
    var lastName: String? = null,

    @Column
    var birthDate: Date? = null,

    @Column
    var deathDate: Date? = null,

    @Lob
    var biography: ByteArray? = null,
)