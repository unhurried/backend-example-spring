package io.github.unhurried.example.backend.spring.mapper

import io.github.unhurried.example.backend.spring.resource.bean.ErrorBean
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class WebApplicationExceptionMapper: ExceptionMapper<WebApplicationException> {
    override fun toResponse(exception: WebApplicationException): Response {
        val entity = ErrorBean("web_application_error")
        return Response.status(exception.response.status).entity(entity).build()
    }
}
