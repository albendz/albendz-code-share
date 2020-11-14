package com.alicia.data

import com.alicia.model.MemberResponse
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "member")
data class Member(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val id: UUID? = null,

        @Column(name = "first_name")
        val firstName: String? = null,

        @Column(name = "last_name")
        val lastName: String? = null,

        @Column(name = "overdue_fees")
        val overdueFees: Int? = null,
) {
        fun toMemberResponse(): MemberResponse =
                MemberResponse(
                        name = "$lastName, $firstName",
                        overdueFees = overdueFees,
                )
}