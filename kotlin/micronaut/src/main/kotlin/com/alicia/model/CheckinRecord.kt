package com.alicia.model

import com.alicia.data.Loan
import java.util.UUID

data class CheckinRecord(
    val copyId: UUID?,
    val isbn: String?,
    val memberId: UUID?,
    val loanId: Loan?,
)