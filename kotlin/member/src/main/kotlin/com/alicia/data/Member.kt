package com.alicia.data

import java.time.Instant
import java.util.UUID

open class Member(
    val uuid: UUID,
    val name: String,
    val permissions: List<String>,
    val accountStatus: String,
    val lastLogin: Instant,
    val createdDate: Instant,
    val email: String,
)