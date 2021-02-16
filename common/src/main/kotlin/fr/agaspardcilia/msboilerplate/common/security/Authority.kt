package fr.agaspardcilia.msboilerplate.common.security

enum class Authority(vararg val permissions: Permission) {
    ADMIN(
        Permission.AUTHENTICATION_USER_GET_ALL,
    ),
    USER(
        Permission.AUTHENTICATION_USER_GET,
        Permission.AUTHENTICATION_USER_GET_SELF,

        Permission.NOTES_CREATE,
        Permission.NOTES_UPDATE,
        Permission.NOTES_DELETE
    ),
    ROLE_ANONYMOUS(
        Permission.AUTHENTICATION_JWT_AUTH,
        Permission.AUTHENTICATION_USER_REGISTER,
        Permission.AUTHENTICATION_USER_REQUEST_PWD_RESET,
        Permission.AUTHENTICATION_USER_RESET_PWD,
        Permission.AUTHENTICATION_USER_CHANGE_PWD,
        Permission.AUTHENTICATION_USER_ACTIVATE,

        Permission.NOTES_GET
    ),
}

