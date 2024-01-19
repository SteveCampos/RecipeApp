package com.stevecampos.core.common

sealed class Failure {
    data class BadRequest(val any: Any? = null) : Failure()
    data class AuthorizationRequired(val any: Any? = null) : Failure()
    data class AccessDeniedOrForbidden(val any: Any? = null) : Failure()
    data class NotFound(val any: Any? = null) : Failure()
    data class Conflict(val any: Any? = null) : Failure()
    data class DestinationLocked(val any: Any? = null) : Failure()
    data class Unprocessable(val any: Any? = null) : Failure()
    data class Locked(val any: Any? = null) : Failure()
    data class TooManyRequest(val any: Any? = null) : Failure()
    data class InternalServerError(val any: Any? = null) : Failure()
    data class ServiceUnavailable(val any: Any? = null) : Failure()
    data class PreconditionFailed(val any: Any? = null) : Failure()
    object Others : Failure()
}