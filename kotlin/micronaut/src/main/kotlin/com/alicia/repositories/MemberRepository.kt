package com.alicia.repositories

import com.alicia.data.Member
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
abstract class MemberRepository: CrudRepository<Member, UUID> {
}