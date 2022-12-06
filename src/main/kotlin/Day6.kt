fun main() {
    readFile("input/6.txt")
        .map { findPackageMarker(it) to findMessageMarker(it) }
        .forEach { println("package marker ${it.first} AND message marker: ${it.second}") }
}

fun findPackageMarker(input: String): Int {
    return input
        .windowed(4, 1)
        .indexOfFirst { it.toList().distinct().size == 4 } + 4
}

fun findMessageMarker(input: String): Int {
    return input
        .windowed(14, 1)
        .indexOfFirst { it.toList().distinct().size == 14 } + 14
}