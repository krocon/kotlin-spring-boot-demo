package de.krocon.kotlindemo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {

    @Test
    fun `GET tasks returns 200`() {
        mockMvc.get("/api/tasks").andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    fun `POST creates a task and returns 201`() {
        mockMvc.post("/api/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"title": "Kotlin kennenlernen"}"""
        }.andExpect {
            status { isCreated() }
            header { exists("Location") }
            jsonPath("$.title") { value("Kotlin kennenlernen") }
            jsonPath("$.completed") { value(false) }
        }
    }

    @Test
    fun `PATCH marks a task as completed`() {
        val location = mockMvc.post("/api/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"title": "Tests schreiben"}"""
        }.andReturn().response.getHeader("Location")

        mockMvc.patch("$location/complete").andExpect {
            status { isOk() }
            jsonPath("$.completed") { value(true) }
        }
    }

    @Test
    fun `GET unknown task returns 404`() {
        mockMvc.get("/api/tasks/9999").andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `POST with blank title returns 400`() {
        mockMvc.post("/api/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"title": "  "}"""
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `POST without title returns 400`() {
        mockMvc.post("/api/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = "{}"
        }.andExpect {
            status { isBadRequest() }
        }
    }
}
