package com.alicia.data

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "book")
data class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var bookId: UUID? = null,

    @Column(name = "title")
    var  title: String? = null,
)