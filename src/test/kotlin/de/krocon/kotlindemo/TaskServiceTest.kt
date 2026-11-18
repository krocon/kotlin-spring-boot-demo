package de.krocon.kotlindemo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TaskServiceTest {

    private lateinit var taskService: TaskService

    @BeforeEach
    fun setUp() {
        taskService = TaskService()
    }

    @Test
    fun `should create a task`() {
        val task = taskService.create("Kotlin lernen")

        assertThat(task.id).isEqualTo(1)
        assertThat(task.title).isEqualTo("Kotlin lernen")
        assertThat(task.completed).isFalse()
    }

    @Test
    fun `should list tasks in creation order`() {
        taskService.create("Erste Aufgabe")
        taskService.create("Zweite Aufgabe")

        val titles = taskService.findAll().map { it.title }

        assertThat(titles)
            .containsExactly("Erste Aufgabe", "Zweite Aufgabe")
    }

    @Test
    fun `should complete a task`() {
        val task = taskService.create("Tests schreiben")

        val completed = taskService.complete(task.id)

        assertThat(completed?.completed).isTrue()
        assertThat(taskService.findById(task.id)?.completed)
            .isTrue()
    }

    @Test
    fun `should return null for an unknown id`() {
        assertThat(taskService.findById(99)).isNull()
        assertThat(taskService.complete(99)).isNull()
    }
}
