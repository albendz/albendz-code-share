package com.alicia.services

import com.alicia.constants.Availability
import com.alicia.exceptions.EmptyImportCsvException
import com.alicia.exceptions.FailureToReadImportCsvException
import com.alicia.model.*
import io.micronaut.http.multipart.CompletedFileUpload
import kotlin.jvm.Throws

interface BookService {

    fun search(
            availabilities: List<Availability>,
            pageNumber: Int,
            itemsPerPage: Int
    ): PaginatedBookResponse

    fun getBook(isbn: String): BookResponse

    fun addBook(addBookRequest: AddBookRequest): BookResponse

    @Throws(EmptyImportCsvException::class, FailureToReadImportCsvException::class)
    fun bulkUpload(csv: CompletedFileUpload): BulkUploadResponse

    fun checkoutBook(isbn: String, checkoutRequest: CheckoutRequest): LoanResponse
}

