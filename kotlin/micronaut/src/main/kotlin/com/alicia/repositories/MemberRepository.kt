package com.alicia.repositories

import com.alicia.data.Member
import com.alicia.data.Name
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
abstract class MemberRepository: CrudRepository<Member, Name> {
}