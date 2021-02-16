package fr.agaspardcilia.msboilerplate.authentication.user

import fr.agaspardcilia.msboilerplate.authentication.mail.IMailService
import fr.agaspardcilia.msboilerplate.authentication.mail.MailDto
import fr.agaspardcilia.msboilerplate.authentication.config.properties.ServiceProperties
import fr.agaspardcilia.msboilerplate.authentication.user.dto.*
import fr.agaspardcilia.msboilerplate.common.exception.ApiConflictException
import fr.agaspardcilia.msboilerplate.common.exception.ApiNotFoundException
import fr.agaspardcilia.msboilerplate.common.security.Authority
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val activationKeyRepository: ActivationKeyRepository,
    private val forgottenPasswordTokenRepository: ForgottenPasswordTokenRepository,

    private val mailService: IMailService,

    private val passwordEncoder: PasswordEncoder,
    private val serviceProperties: ServiceProperties
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun get(id: UUID): UserDto? = userRepository.findByIdOrNull(id).let { it?.toDto() }

    fun getByMail(mail: String): UserDto? = userRepository.findByMail(mail).let { it?.toDto() }

    fun doesExistByMail(mail: String): Boolean = userRepository.existsByMail(mail)

    fun getAll(): List<UserDto> = userRepository.findAll().map { it.toDto() }

    fun registerUser(user: UserCreationDto): UserDto {
        val toSave: User = user.toEntity()
        if (doesExistByMail(user.mail!!)) {
            throw ApiConflictException("A user already exists with this mail")
        }

        toSave.password = passwordEncoder.encode(user.password)
        toSave.authorities.add(Authority.USER)

        val result = userRepository.save(toSave)
        val activationKey = createAndSaveActivationKey(result)

        log.debug("Registering ${user.mail} with ${activationKey.id} as his activation key!")
        sendUserRegistrationMail(result, activationKey)

        return result.toDto()
    }

    fun getCurrentUser(): UserDto? {
        val userMail = getCurrentUserLogin()
        return if (userMail != null) userRepository.findByMail(userMail).let { it?.toDto() } else null
    }

    private fun getCurrentUserLogin(): String? = SecurityContextHolder.getContext().authentication?.name

    fun activateUser(activationKeyId: String) {
        val activationKey = activationKeyRepository.findById(UUID.fromString(activationKeyId))
            .orElseThrow { ApiNotFoundException("Unable to find activation key '$activationKeyId'") }
        val user = activationKey.user
            ?: throw ApiNotFoundException("Can't find user, although, this is not suppose to happen")

        user.isActive = true
        userRepository.save(user)
        activationKeyRepository.delete(activationKey)

    }

    fun changePassword(paswordChangeDto: PasswordChangeDto): UserDto {
        val user = userRepository.findByMail(paswordChangeDto.username!!)
            ?: throw ApiNotFoundException("Unable to find user '${paswordChangeDto.username}'")

        if (!passwordEncoder.matches(paswordChangeDto.oldPassword, user.password)) {
            throw BadCredentialsException("Wrong username/password combination!")
        }

        user.password = passwordEncoder.encode(paswordChangeDto.newPassword)
        return userRepository.save(user).toDto()
    }


    fun changePassword(forgottenPasswordDto: ForgottenPasswordDto): UserDto {
        val forgottenPasswordToken = forgottenPasswordTokenRepository.findById(UUID.fromString(forgottenPasswordDto.token))
            .orElseThrow { ApiNotFoundException("Unable to find password change token '${forgottenPasswordDto.token}'") }
        val user = forgottenPasswordToken.user
            ?: throw throw ApiNotFoundException("Can't find user, although, this is not suppose to happen")

        user.password = passwordEncoder.encode(forgottenPasswordDto.newPassword)

        forgottenPasswordTokenRepository.delete(forgottenPasswordToken)
        return userRepository.save(user).toDto()
    }

    fun createPasswordResetTokenAndSendMail(mail: String) {
        val user = userRepository.findByMail(mail) ?: throw ApiNotFoundException("Unable to find user '$mail'")
        val forgottenPasswordToken = forgottenPasswordTokenRepository.save(ForgottenPasswordToken(user = user))

        sendPasswordResetMail(user, forgottenPasswordToken)
    }

    private fun createAndSaveActivationKey(user: User): ActivationKey {
        return activationKeyRepository.save(ActivationKey(user = user))
    }

    private fun sendUserRegistrationMail(user: User, activationKey: ActivationKey) {
        val mail = MailDto(
            from = serviceProperties.mail.server.mailAddress,
            to = user.mail!!,
            subject = "Confirm account creation",
            content =
            """
                Hello,

                An account has been created using this mail address, go to ${serviceProperties.mail.server.url}/users/activate/${activationKey.id} to activate it!

                Have a nice day.
            """.trimIndent()
        )

        mailService.sendMail(mail)
    }

    private fun sendPasswordResetMail(user: User, forgottenPasswordToken: ForgottenPasswordToken) {
        val mail = MailDto(
            from = serviceProperties.mail.server.mailAddress,
            to = user.mail!!,
            subject = "Password reset request",
            content =
            """
                Hello,

                A password reset has been request for this address, go to ${serviceProperties.mail.server.url}/users/forgotten/${forgottenPasswordToken.id} to set a new one!

                Have a nice day.
            """.trimIndent()
        )

        mailService.sendMail(mail)
    }
}
