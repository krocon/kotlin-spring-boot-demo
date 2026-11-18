package de.krocon.kotlindemo

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.net.URI

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val taskService: TaskService,
) {

    @GetMapping
    fun getTasks(): List<Task> =
        taskService.findAll()

    @GetMapping("/{id}")
    fun getTask(@PathVariable id: Long): Task =
        taskService.findById(id) ?: throw notFound(id)

    @PostMapping
    fun createTask(
        @RequestBody request: CreateTaskRequest,
    ): ResponseEntity<Task> {
        if (request.title.isBlank()) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "title must not be blank",
            )
        }
        val task = taskService.create(request.title)
        val location = URI.create("/api/tasks/${task.id}")
        return ResponseEntity.created(location).body(task)
    }

    @PatchMapping("/{id}/complete")
    fun completeTask(@PathVariable id: Long): Task =
        taskService.complete(id) ?: throw notFound(id)

    private fun notFound(id: Long) =
        ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Task $id not found",
        )
}
