package com.alicia.data

import com.alicia.model.MemberResponse
import io.micronaut.data.annotation.Join
import java.util.*
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "member")
data class Member(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val id: UUID? = null,

        @Column(name = "email", unique = true)
        val email: String,

        @Column(name = "first_name")
        val firstName: String? = null,

        @Column(name = "last_name")
        val lastName: String? = null,

        @Column(name = "overdue_fees")
        val overdueFees: Int? = null,
) {
        @Transient
        var loans: List<Loan> = emptyList()

        fun toMemberResponse(): MemberResponse =
                MemberResponse(
                        email = email,
                        name = "$lastName, $firstName",
                        overdueFees = overdueFees,
                )
}