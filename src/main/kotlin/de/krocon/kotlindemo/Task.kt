package de.krocon.kotlindemo

data class Task(
    val id: Long,
    val title: String,
    val completed: Boolean = false,
)

data class CreateTaskRequest(
    val title: String,
)
