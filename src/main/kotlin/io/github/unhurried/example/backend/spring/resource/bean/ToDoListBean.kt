package io.github.unhurried.example.backend.spring.resource.bean

/** Data Transfer Object for request and response bodies of ToDoResource  */
data class ToDoListBean (
    var total: Long? = null,
    var items: Iterable<ToDoBean>? = null
)