fun main() {
    val dictionary = "abcdefghijklmnopqrstuvwxyz".toList()

    val result = readFile("input/3.txt")
        .windowed(3, 3)
        .map { group ->
            group.map { contents -> contents.toList() }
        }.map {
            it[0].intersect(it[1].toSet()).intersect(it.last().toSet()).first()
        }.map {
            var index = dictionary.indexOf(it.lowercaseChar()) + 1
            if (it.isUpperCase())
                index += 26
            index
        }.also {
            println(it)
        }.sum()

    println(result)
}