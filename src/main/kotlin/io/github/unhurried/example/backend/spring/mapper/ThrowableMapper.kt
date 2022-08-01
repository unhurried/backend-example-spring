package io.github.unhurried.example.backend.spring.mapper

import io.github.unhurried.example.backend.spring.resource.bean.ErrorBean
import org.slf4j.LoggerFactory
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class ThrowableMapper: ExceptionMapper<Throwable> {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
    override fun toResponse(exception: Throwable?): Response {
        log.error(exception.toString())
        return Response.serverError().entity(ErrorBean("internal_server_error")).build();
    }
}
