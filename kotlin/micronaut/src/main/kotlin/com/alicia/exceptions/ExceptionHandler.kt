package com.alicia.exceptions

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.HttpResponse
import org.hibernate.HibernateException
import javax.inject.Singleton

@Produces
@Singleton
@Requires(classes = [LibraryApiException::class, ExceptionHandler::class])
class LibraryExceptionHandler :  ExceptionHandler<LibraryApiException, HttpResponse<String>> {

    override fun handle(request: HttpRequest<*>?, exception: LibraryApiException?): HttpResponse<String>? =
        exception?.let {
            HttpResponse.status(it.httpStatus, it.message)
        } ?: HttpResponse.badRequest()

}

@Produces
@Singleton
@Requires(classes = [ExceptionHandler::class])
class DatabaseExceptionHandler :  ExceptionHandler<HibernateException, HttpResponse<String>> {

    override fun handle(request: HttpRequest<*>?, exception: HibernateException?): HttpResponse<String>? =
            HttpResponse.serverError("Unexpected error occurred: ${exception?.message}")

}
