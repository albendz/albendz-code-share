package com.alicia.data

import com.alicia.model.LoanResponse
import java.time.format.DateTimeFormatter
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "loan")
data class Loan(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val id: UUID? = null,

        @Column(name = "copy")
        val copy: Copy? = null,

        @Temporal(value = TemporalType.DATE)
        @Column(name = "loan_date")
        val loanDate: Date? = null,

        @Column(name = "length_days")
        val lengthDays: Int? = null,

        @ManyToOne
        @Column(name =  "member")
        val member: Member? = null,
) {
        fun toLoanResponse(): LoanResponse =
                LoanResponse(
                        id = id,
                        copyId = copy?.copyId,
                        loanDate = loanDate?.let { DateTimeFormatter.ISO_DATE.format(it.toInstant()) },
                        loanLength = lengthDays,
                        member = "${member?.memberName}",
                )
}