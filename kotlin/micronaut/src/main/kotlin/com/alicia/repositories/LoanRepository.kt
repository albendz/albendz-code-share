package com.alicia.repositories

import com.alicia.data.Loan
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
abstract class LoanRepository: CrudRepository<Loan, UUID> {
}