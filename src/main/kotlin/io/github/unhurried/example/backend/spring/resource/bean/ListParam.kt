package io.github.unhurried.example.backend.spring.resource.bean

import javax.ws.rs.DefaultValue
import javax.ws.rs.QueryParam

/** Data Transfer Object for request parameters for list resources.  */
data class ListParam (
    @DefaultValue("10")
    @QueryParam("size")
    val size: Int = 0,

    @DefaultValue("0")
    @QueryParam("page")
    val page: Int = 0
)