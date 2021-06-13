package com.alicia.services

import com.alicia.exceptions.BookNotFoundException
import com.alicia.exceptions.CopyNotFoundException
import com.alicia.model.CopyResponse
import com.alicia.repositories.BookRepository
import com.alicia.repositories.CopyRepository
import java.util.UUID
import javax.inject.Inject

class CopyServiceImpl : CopyService {

    @Inject
    lateinit var copyRepository: CopyRepository

    @Inject
    lateinit var bookRepository: BookRepository

    override fun getAllBookCopies(isbn: String): List<CopyResponse> = // TODO: if isbn does not exist
        //bookRepository.existsById(isbn).takeIf { it }?.let {
            copyRepository.findAllByIsbn(isbn).map { it.toCopyResponse() }
        //} ?: throw BookNotFoundException(isbn)

    override fun getBookCopy(isbn: String, copyId: UUID): CopyResponse =
        copyRepository.findFirstByCopyIdAndIsbn(copyId, isbn)?.toCopyResponse() ?: throw CopyNotFoundException(
            isbn,
            copyId
        )
}
