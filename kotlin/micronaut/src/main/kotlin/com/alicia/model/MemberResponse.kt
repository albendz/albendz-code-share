package com.alicia.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.UUID

@JsonInclude(JsonInclude.Include.ALWAYS)
data class MemberResponse(
    val id: UUID?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
)