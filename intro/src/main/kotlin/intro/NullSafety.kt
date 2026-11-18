package intro

fun main() {
    val name: String = "Marc"
    val optionalName: String? = null

    println(name.uppercase())
    println(optionalName?.uppercase())

    val displayName = optionalName ?: "Unbekannt"
    println(displayName)
}
