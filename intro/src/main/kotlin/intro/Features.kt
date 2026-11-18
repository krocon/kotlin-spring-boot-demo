package intro

fun String.shout(): String = uppercase() + "!"

fun describe(value: Any): String =
    when (value) {
        is String -> "Text mit ${value.length} Zeichen"
        is Int -> "Zahl, verdoppelt: ${value * 2}"
        else -> "etwas anderes"
    }

fun main() {
    val name = "Kotlin"
    var count = 1
    count += 1

    println("Hallo, $name, Version $count")
    println(name.shout())
    println(describe("JVM"))
    println(describe(21))
}
