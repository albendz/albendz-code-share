package com.alicia

import io.micronaut.runtime.Micronaut.*
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License

@OpenAPIDefinition(
		info = Info(
				title = "Library",
				version = "0.1",
				description = "My Library API",
				license = License(name = "Apache 2.0", url = "https://library.com"),
				contact = Contact(url = "https://libraries.com", name = "Alicia", email = "alicia@libraries.com")
		)
)
object Application {

	@JvmStatic
	fun main(args: Array<String>) {
		build()
				.args(*args)
				.packages("com.alicia")
				.start()
	}
}
