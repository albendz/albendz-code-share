package com.alicia.data

import com.alicia.model.GenreResponse
import java.util.UUID
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "genre")
data class Genre(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val id: UUID? = null,

        // TODO: read from config
        @Size(max = 100)
        @Column(name = "name")
        val name: String? = null,

        @Column(name = "age_group")
        val ageGroup: String? = null,
) {
    fun toGenreResponse(): GenreResponse =
        GenreResponse(
                id = id,
                name = name
        )
}