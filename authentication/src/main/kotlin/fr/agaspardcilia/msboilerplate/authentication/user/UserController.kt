package fr.agaspardcilia.msboilerplate.authentication.user

import fr.agaspardcilia.msboilerplate.authentication.user.dto.ForgottenPasswordDto
import fr.agaspardcilia.msboilerplate.authentication.user.dto.PasswordChangeDto
import fr.agaspardcilia.msboilerplate.authentication.user.dto.UserCreationDto
import fr.agaspardcilia.msboilerplate.authentication.user.dto.UserDto
import fr.agaspardcilia.msboilerplate.common.security.Permission
import fr.agaspardcilia.msboilerplate.common.security.annotations.PermissionRequired
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.annotation.security.PermitAll
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{id}")
    @PermissionRequired(Permission.AUTHENTICATION_USER_GET)
    fun get(@PathVariable id: UUID): UserDto? {
        log.info("Getting user {}", id)
        return userService.get(id)
    }

    @GetMapping
    @PermissionRequired(Permission.AUTHENTICATION_USER_GET_ALL)
    fun getAll(): List<UserDto> {
        log.info("Getting all users")
        return userService.getAll()
    }

    @GetMapping("/mail/{mail}")
    @PermissionRequired(Permission.AUTHENTICATION_USER_GET)
    fun getByMail(@PathVariable mail: String): UserDto? {
        log.info("Getting user with mail {}", mail)
        return userService.getByMail(mail)
    }

    @PostMapping("/register")
    @PermissionRequired(Permission.AUTHENTICATION_USER_REGISTER)
    fun register(@Valid @RequestBody user: UserCreationDto): UserDto {
        log.info("Registering user {}", user.mail)
        return userService.registerUser(user)
    }

    @GetMapping("/current-user")
    @PermissionRequired(Permission.AUTHENTICATION_USER_GET_SELF)
    fun getCurrentUser(): UserDto? {

        log.info("Getting current user")
        return userService.getCurrentUser()
    }

    @PutMapping("/activate/{key}")
    @PermissionRequired(Permission.AUTHENTICATION_USER_ACTIVATE)
    fun activateUser(@PathVariable key: String) {
        log.info("Activating user with key {}", key)
        userService.activateUser(key)
    }

    @PutMapping("/password")
    @PermissionRequired(Permission.AUTHENTICATION_USER_CHANGE_PWD)
    fun changePassword(@RequestBody @Valid passwordChangeDto: PasswordChangeDto) {
        log.info("Attempting to change ${passwordChangeDto.username}'s password")
        userService.changePassword(passwordChangeDto)
    }

    @PutMapping("/reset")
    @PermissionRequired(Permission.AUTHENTICATION_USER_RESET_PWD)
    fun resetPassword(@RequestBody @Valid passwordResetDto: ForgottenPasswordDto) {
        log.info("Attempting to change a forgotten password with ${passwordResetDto.token} as the token")
        userService.changePassword(passwordResetDto)
    }

    @PostMapping("/forgotten/{mail}")
    @PermissionRequired(Permission.AUTHENTICATION_USER_REQUEST_PWD_RESET)
    fun requestPasswordReset(@PathVariable("mail") mail: String) {
        log.info("Requesting password reset for $mail")
        userService.createPasswordResetTokenAndSendMail(mail)
    }
}
