package com.alicia.services

import com.alicia.model.CopyResponse
import java.util.UUID

interface CopyService {
    fun getAllBookCopies(isbn: String): List<CopyResponse>

    fun getBookCopy(isbn: String, copyId: UUID): CopyResponse

}