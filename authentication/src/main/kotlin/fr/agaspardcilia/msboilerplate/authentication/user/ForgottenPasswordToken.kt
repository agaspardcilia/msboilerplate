package fr.agaspardcilia.msboilerplate.authentication.user

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
data class ForgottenPasswordToken(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id: UUID? = null,

    @field:OneToOne
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @field:CreationTimestamp
    var creationDate: Timestamp? = null
)
