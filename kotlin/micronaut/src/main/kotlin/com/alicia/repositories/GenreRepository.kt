package com.alicia.repositories

import com.alicia.data.Genre
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
abstract class GenreRepository: CrudRepository<Genre, UUID> {

    abstract fun findFirstByName(name: String?): Genre?

    abstract fun findFirstById(id: UUID): Genre?

    @Query(
        value = "SELECT g FROM Genre g",
        countQuery = "SELECT count(*) FROM Genre"
    )
    abstract fun getAllGenres(pageable: Pageable): Page<Genre>

}