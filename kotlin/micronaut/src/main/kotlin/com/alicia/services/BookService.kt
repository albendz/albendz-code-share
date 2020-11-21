package com.alicia.services

import com.alicia.model.AddBookRequest
import com.alicia.model.BookResponse
import com.alicia.model.BulkUploadResponse
import io.micronaut.http.multipart.CompletedFileUpload

interface BookService {

    fun getBook(isbn: String): BookResponse

    fun addBook(addBookRequest: AddBookRequest): BookResponse

    fun bulkUpload(csv: CompletedFileUpload): BulkUploadResponse
}

