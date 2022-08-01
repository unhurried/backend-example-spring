package io.github.unhurried.example.backend.spring.resource

import io.github.unhurried.example.backend.spring.helper.CopyObjectHelper
import io.github.unhurried.example.backend.spring.repository.ToDoRepository
import io.github.unhurried.example.backend.spring.repository.entity.ToDo
import io.github.unhurried.example.backend.spring.resource.bean.ListParam
import io.github.unhurried.example.backend.spring.resource.bean.ToDoBean
import io.github.unhurried.example.backend.spring.resource.bean.ToDoListBean
import io.github.unhurried.example.backend.spring.resource.exception.NotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


/** A JAX-RS (Jersey) resource class for To Do items  */
// Resource classes must be annotated with one of @Component, @Service, @Controller, @Repository.
// (Note: Spring AOP doesn't work if @Named is used instead.)
@Component
@Path("todos") // Base path for all methods in the class
@Consumes(MediaType.APPLICATION_JSON) // Content-Type of request body
@Produces(MediaType.APPLICATION_JSON) // Content-Type of response body
@Transactional // Make all the methods in the class transactional.
class ToDoResource (private val repository: ToDoRepository, private val beanHelper: CopyObjectHelper) {
    private val beanToEntity = { src: ToDoBean, target: ToDo ->
        target.category = ToDo.Category.valueOf(src.category.toString())
    }
    private val entityToBean = { src: ToDo, target: ToDoBean ->
        target.category = ToDoBean.Category.valueOf(src.category.toString()) }

    private val getUserId = {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth is AnonymousAuthenticationToken) throw WebApplicationException(Response.Status.UNAUTHORIZED)
        auth.name
    }

    /** GET /api/todos: Get a list of items. */
    @GET
    fun getList(@BeanParam listParam: ListParam): Response {
        val pageable: Pageable = PageRequest.of(listParam.page, listParam.size)
        val entityList = repository.findByUserId(getUserId(), pageable)
        val list = ToDoListBean()
        list.items = beanHelper.createAndCopyIterable(entityList, ToDoBean::class.java, entityToBean)
        list.total = entityList.totalElements
        return Response.ok().entity(list).build()
    }

    /** POST /api/todos: Create a new item.  */
    @POST
    fun create(reqBean: ToDoBean): Response {
        var entity = beanHelper.createAndCopy(reqBean, ToDo::class.java, beanToEntity)
        entity.id = UUID.randomUUID().toString()
        entity.userId = getUserId()
        entity = repository.save(entity)
        val resBean = beanHelper.createAndCopy(entity, ToDoBean::class.java, entityToBean)
        return Response.created(null).entity(resBean).build()
    }

    /** GET /api/todos/{id}: Get a todo item.  */
    @GET
    @Path("{id}")
    operator fun get(@PathParam("id") id: String): Response {
        val result = repository.findByIdAndUserId(id, getUserId())
        return if (result.isPresent) {
            val bean = beanHelper.createAndCopy(result.get(), ToDoBean::class.java, entityToBean)
            Response.ok().entity(bean).build()
        } else {
            throw NotFoundException()
        }
    }

    /** PUT /api/todos/{id}: Update a todo item.  */
    @PUT
    @Path("{id}")
    fun update(@PathParam("id") id: String, reqBean: ToDoBean): Response {
        val res = repository.findByIdAndUserId(id, getUserId())
        if (res.isEmpty) {
            throw WebApplicationException(Response.Status.NOT_FOUND)
        }
        val entity = res.get()
        beanHelper.copyProperties(reqBean, entity)
        entity.id = id
        entity.userId = getUserId()
        repository.save(entity)
        return Response.ok().entity(reqBean).build()
    }

    /** DELETE /api/todos/{id}: Delete a todo item.  */
    @DELETE
    @Path("{id}")
    fun remove(@PathParam("id") id: String): Response {
        val result = repository.findByIdAndUserId(id, getUserId())
        return if (result.isPresent) {
            repository.deleteById(id)
            Response.noContent().build()
        } else {
            throw NotFoundException()
        }
    }
}
