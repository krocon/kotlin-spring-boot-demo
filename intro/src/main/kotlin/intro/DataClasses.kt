package intro

data class User(
    val name: String,
    val age: Int,
)

fun main() {
    val marc = User("Marc", 42)
    println(marc)
    println(marc == User("Marc", 42))

    val older = marc.copy(age = 43)
    println(older)

    // Java aus Kotlin: Record und Klasse direkt benutzen
    val record = JavaUserRecord("Marc", 42)
    println(record)
    println(JavaUser("Marc", 42).name)
}
