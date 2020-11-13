package com.alicia.repositories

import com.alicia.data.Genre
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
abstract class GenreRepository: CrudRepository<Genre, UUID> {
}