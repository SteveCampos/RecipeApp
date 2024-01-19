package com.stevecampos.recipe.data.mapper

import com.stevecampos.core.common.Failure

fun Int.findFailure(): Failure {
    return when (this) {
        400 -> Failure.BadRequest()
        401 -> Failure.AuthorizationRequired()
        403 -> Failure.AccessDeniedOrForbidden()
        404 -> Failure.NotFound()
        409 -> Failure.Conflict()
        412 -> Failure.PreconditionFailed()
        421 -> Failure.DestinationLocked()
        422 -> Failure.Unprocessable()
        423 -> Failure.Locked()
        429 -> Failure.TooManyRequest()
        500 -> Failure.InternalServerError()
        503 -> Failure.ServiceUnavailable()
        else -> Failure.Others
    }
}
