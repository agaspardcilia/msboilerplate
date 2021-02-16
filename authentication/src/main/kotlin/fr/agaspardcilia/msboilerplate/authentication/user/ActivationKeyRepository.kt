package fr.agaspardcilia.msboilerplate.authentication.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ActivationKeyRepository : JpaRepository<ActivationKey, UUID>
