package com.alicia.repositories

import com.alicia.data.Hold
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
abstract class HoldRepository: CrudRepository<Hold, UUID> {
}