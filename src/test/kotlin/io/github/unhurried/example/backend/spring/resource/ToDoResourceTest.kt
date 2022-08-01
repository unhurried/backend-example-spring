package io.github.unhurried.example.backend.spring.resource

import io.github.unhurried.example.backend.spring.repository.ToDoRepository
import io.github.unhurried.example.backend.spring.repository.entity.ToDo
import io.github.unhurried.example.backend.spring.resource.bean.ToDoBean
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status

// @SpringBootTest: Required to enable Spring features in the test class.
// webEnvironment specifies how the servlet environment is provided.
// (Provide a real or mock servlet environment, or not using a servlet.)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@WithMockUser
class ToDoResourceTest (@Autowired private val resource: ToDoResource) {

    // Use @MockBean (Spring Boot annotation) instead of @Mock (Mockito annotation).
    @MockBean lateinit var repository: ToDoRepository

    @Test
    fun testCreate() {
        val t = ToDo()
        t.seq = 1L
        t.id = "123"
        t.title = "Test ToDo Item"
        t.category = ToDo.Category.one
        t.content = "This is a test ToDo item."
        given(repository.save(any(ToDo::class.java))).willReturn(t)
        val bean = ToDoBean()
        bean.id = "123"
        bean.title = "Test ToDo Item"
        bean.category = ToDoBean.Category.one
        bean.content = "This is a test ToDo item."
        val res: Response = resource.create(bean)
        assertThat(res.status).isEqualTo(Status.CREATED.statusCode)
        val entity = res.entity
        if (entity !is ToDoBean) {
            fail<Any>("Response entity must be TodoBean type.")
        } else {
            assertThat(entity.id).isEqualTo("123")
            assertThat(entity.title).isEqualTo(t.title)
            assertThat(entity.category).isEqualTo(ToDoBean.Category.one)
            assertThat(entity.content).isEqualTo(t.content)
        }
    }
}
