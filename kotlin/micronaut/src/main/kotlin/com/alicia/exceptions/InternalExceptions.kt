package com.alicia.exceptions

class NonUniqueMemberEmailException: Exception("User already exists with email")

class NoCopyAvailableForBookException(isbn: String): Exception("Copy does not exist for book with ISBN: $isbn")
