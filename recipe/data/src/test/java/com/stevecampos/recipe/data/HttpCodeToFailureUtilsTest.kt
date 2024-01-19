package com.stevecampos.recipe.data

import com.stevecampos.core.common.Failure
import com.stevecampos.recipe.data.mapper.findFailure
import org.junit.Assert.assertEquals
import org.junit.Test

class HttpCodeToFailureUtilsTest {

    @Test
    fun `findFailure should return BadRequest for HTTP 400`() {
        assertEquals(Failure.BadRequest(), 400.findFailure())
    }

    @Test
    fun `findFailure should return AuthorizationRequired for HTTP 401`() {
        assertEquals(Failure.AuthorizationRequired(), 401.findFailure())
    }

    @Test
    fun `findFailure should return AccessDeniedOrForbidden for HTTP 403`() {
        assertEquals(Failure.AccessDeniedOrForbidden(), 403.findFailure())
    }

    @Test
    fun `findFailure should return NotFound for HTTP 404`() {
        assertEquals(Failure.NotFound(), 404.findFailure())
    }

    @Test
    fun `findFailure should return Conflict for HTTP 409`() {
        assertEquals(Failure.Conflict(), 409.findFailure())
    }

    @Test
    fun `findFailure should return PreconditionFailed for HTTP 412`() {
        assertEquals(Failure.PreconditionFailed(), 412.findFailure())
    }

    @Test
    fun `findFailure should return DestinationLocked for HTTP 421`() {
        assertEquals(Failure.DestinationLocked(), 421.findFailure())
    }

    @Test
    fun `findFailure should return Unprocessable for HTTP 422`() {
        assertEquals(Failure.Unprocessable(), 422.findFailure())
    }

    @Test
    fun `findFailure should return Locked for HTTP 423`() {
        assertEquals(Failure.Locked(), 423.findFailure())
    }

    @Test
    fun `findFailure should return TooManyRequest for HTTP 429`() {
        assertEquals(Failure.TooManyRequest(), 429.findFailure())
    }

    @Test
    fun `findFailure should return InternalServerError for HTTP 500`() {
        assertEquals(Failure.InternalServerError(), 500.findFailure())
    }

    @Test
    fun `findFailure should return ServiceUnavailable for HTTP 503`() {
        assertEquals(Failure.ServiceUnavailable(), 503.findFailure())
    }

    @Test
    fun `findFailure should return Others for an unknown HTTP code`() {
        assertEquals(Failure.Others, 999.findFailure())
    }
}