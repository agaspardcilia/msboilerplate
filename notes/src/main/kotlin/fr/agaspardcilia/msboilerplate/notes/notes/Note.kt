package fr.agaspardcilia.msboilerplate.notes.notes

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Note(
        @Id
        var id: String? = null,

        var title: String? = null,

        var content: String? = null,

        @field:CreatedDate
        var creation: LocalDateTime? = null,

        @field:CreatedBy
        var author: String? = null
)
