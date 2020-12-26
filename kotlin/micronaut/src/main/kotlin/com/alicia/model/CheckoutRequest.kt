package com.alicia.model

import java.util.UUID

data class CheckoutRequest(
    val memberId: UUID,
    val copyId: UUID?,
): ValidatableRequest() {
    override fun getErrors(): List<String> = emptyList()
}