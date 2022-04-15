package io.github.unhurried.example.backend.spring.repository.entity

import javax.persistence.*

/** A JPA entity corresponding to todo table  */
// @Entity: Indicate that the class is a JPA entity.
@Entity
// @Table: Specifies the table name if it is not same as the class name.
@Table(name = "todo")
data class ToDo (
    // @GeneratedValue(strategy=GenerationType.IDENTITY):
    //   Generates the primary key by using the identity columns of RDBMS.
    //   (ex. MySQL: auto_increment)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var seq: Long? = null,
    // @Id: Indicates that the field uniquely identifies the entity.
    @Id
    var id: String? = null,
    var title: String? = null,
    var category: Category? = null,
    var content: String? = null,

    var userId: String? = null,
) {
    enum class Category {
        one, two, three
    }
}