package com.alicia.repositories

import com.alicia.data.Copy
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
abstract class CopyRepository: CrudRepository<Copy, UUID> {
}