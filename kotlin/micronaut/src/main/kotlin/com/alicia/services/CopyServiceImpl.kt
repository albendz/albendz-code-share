package com.alicia.services

import com.alicia.exceptions.CopyNotFoundException
import com.alicia.model.CopyResponse
import com.alicia.repositories.CopyRepository
import java.util.UUID
import javax.inject.Inject

class CopyServiceImpl : CopyService {

    @Inject
    lateinit var copyRepository: CopyRepository

    override fun getAllBookCopies(isbn: String): List<CopyResponse> =// TODO check if book exists
        copyRepository.findAllByIsbn(isbn).map { it.toCopyResponse() }

    override fun getBookCopy(isbn: String, copyId: UUID): CopyResponse =
        copyRepository.findFirstByCopyIdAndIsbn(copyId, isbn)?.toCopyResponse() ?: throw CopyNotFoundException(
            isbn,
            copyId
        )
}