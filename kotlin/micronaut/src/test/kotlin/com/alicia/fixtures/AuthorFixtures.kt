package com.alicia.fixtures

import com.alicia.data.Author
import java.util.UUID

object AuthorFixtures {
    val defaultUuid = UUID.randomUUID()
    val defaultAuthor = Author(defaultUuid)
}