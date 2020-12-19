package com.alicia.repositories

import com.alicia.data.Member
import com.alicia.exceptions.MemberAlreadyExistsWithEmailException
import com.alicia.exceptions.NonUniqueMemberEmailException
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*
import kotlin.jvm.Throws
import org.hibernate.exception.ConstraintViolationException

@Repository
abstract class MemberRepository: CrudRepository<Member, UUID> {

    abstract fun findFirstById(id: UUID): Member?

    @Throws(NonUniqueMemberEmailException::class)
    fun saveMember(member: Member): Member {
        try {
            return save(member)
        } catch (e: ConstraintViolationException) {
            throw NonUniqueMemberEmailException()
        }
    }
}