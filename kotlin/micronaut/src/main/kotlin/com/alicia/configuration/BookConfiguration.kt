package com.alicia.configuration

import io.micronaut.context.annotation.ConfigurationProperties
import javax.annotation.PostConstruct

@ConfigurationProperties("book")
class BookConfiguration {

    var loanDurationDays = 15

    @PostConstruct
    fun validateProperties() {
        if (loanDurationDays < 1) {
            loanDurationDays = 15
        }
    }

}
