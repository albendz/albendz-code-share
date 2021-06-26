package com.alicia.service

import com.alicia.repository.CredentialRepository

class AuthenticationService(
    val encryptionService: EncryptionService,
    val credentialRepository: CredentialRepository
) {
}