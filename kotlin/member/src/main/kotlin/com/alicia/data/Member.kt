package com.alicia.data

import java.time.Instant

open class Member public constructor(
    val uuid: String?,
    val name: String?,
    val permissions: List<String>?,
    val accountStatus: String?,
    val lastLogin: Instant?,
    val createdDate: Instant?,
    val email: String?,
) {
}