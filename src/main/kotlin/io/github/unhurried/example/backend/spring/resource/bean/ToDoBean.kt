package io.github.unhurried.example.backend.spring.resource.bean

/** Data Transfer Object for request and response bodies of ToDoResource  */
data class ToDoBean (
    var id: String? = null,
    var title: String? = null,
    var category: Category? = null,
    var content: String? = null
) {
    enum class Category {
        one, two, three
    }
}