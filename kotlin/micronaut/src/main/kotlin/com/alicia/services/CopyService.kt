package com.alicia.services

import com.alicia.exceptions.BookNotFoundException
import com.alicia.model.CopyResponse
import java.util.UUID
import kotlin.jvm.Throws

interface CopyService {

    @Throws(BookNotFoundException::class)
    fun getAllBookCopies(isbn: String): List<CopyResponse>

    fun getBookCopy(isbn: String, copyId: UUID): CopyResponse

}