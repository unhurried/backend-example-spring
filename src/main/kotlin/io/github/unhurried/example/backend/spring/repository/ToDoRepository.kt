package io.github.unhurried.example.backend.spring.repository

import io.github.unhurried.example.backend.spring.repository.entity.ToDo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

/** Spring Data JPA repository for ToDoEntity  */
interface ToDoRepository : PagingAndSortingRepository<ToDo, String> {
    fun findByUserId(userId: String, pageable: Pageable): Page<ToDo>
    fun findByIdAndUserId(id: String, userId: String): Optional<ToDo>
}