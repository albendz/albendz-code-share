package com.alicia.data

import com.alicia.model.MemberResponse
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "member")
data class Member(
        @EmbeddedId
        val memberName: Name,

        @Column(name = "overdue_fees")
        val overdueFees: Int? = null,
) {
        fun toMemberResponse(): MemberResponse =
                MemberResponse(
                        name = "$memberName",
                        overdueFees = overdueFees,
                )
}