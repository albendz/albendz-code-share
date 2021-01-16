package com.alicia.data

import com.alicia.model.GenreResponse
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "genre")
data class Genre(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val id: UUID? = null,

        //TODO: Unique constraint?
        @Column(name = "name")
        val name: String? = null,

        @Column(name = "age_group")
        val ageGroup: String? = null,
) {
    fun toGenreResponse(): GenreResponse =
            GenreResponse(
                    name = name
            )
}