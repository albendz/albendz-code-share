package com.alicia.data

import java.time.Instant
import java.util.UUID

open class Employee(
    uuid: String,
    name: String,
    permissions: List<String>,
    accountStatus: String,
    lastLogin: Instant,
    createdDate: Instant,
    email: String,
    department: String,
    location: String,
    employeeId: String,
): Member(uuid, name, permissions, accountStatus, lastLogin, createdDate, email)