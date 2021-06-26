package com.alicia.data

import java.time.Instant
import java.util.UUID

class Admin(
    uuid: UUID,
    name: String,
    permissions: List<String>,
    accountStatus: String,
    lastLogin: Instant,
    createdDate: Instant,
    email: String,
    department: String,
    location: String,
    employeeId: String,
    administrativeLocations: List<String>,
): Employee(uuid, name, permissions, accountStatus, lastLogin, createdDate,  department, location, employeeId, email)