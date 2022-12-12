fun main() {
    val lines = readFile("input/12.txt")
        .map { it.toList() }

    val graph = createGraph(lines)

    part1(graph)
    part2(graph, getAPositions(lines))
}

private fun part1(graph: Graph<String, Int>) {
    val (_, value) = shortestPath(graph, "S", "E")
    println("part1: $value")
}

private fun part2(graph: Graph<String, Int>, allAs: List<String>) {
    val result = allAs.map {
        val (_, value) = shortestPath(graph, it, "E")
        value.toInt() to it
    }.minBy { it.first }

    println("part 2: ${result.first}")
}

fun getAPositions(lines: List<List<Char>>): List<String> {
    val result = mutableListOf<String>()
    lines.forEachIndexed { row, line ->
        line.forEachIndexed { col, char ->
            if (char == 'a')
                result.add(charToNode(char, row, col))
        }
    }
    return result
}

private fun createGraph(lines: List<List<Char>>): Graph<String, Int> {
    val graph = GraphImpl<String, Int>(directed = true, defaultCost = 1)

    lines.forEachIndexed { row, line ->
        line.forEachIndexed { col, char ->
            val currentNode = charToNode(char, row, col)

            val left = line.getOrNull(col - 1)
            val top = lines.getOrNull(row - 1)?.getOrNull(col)
            val right = line.getOrNull(col + 1)
            val bottom = lines.getOrNull(row + 1)?.getOrNull(col)

            left?.run {
                if (climbDistance(char, left) <= 1) {
                    graph.addArc(currentNode to charToNode(this, row, col - 1))
                }
            }

            top?.run {
                if (climbDistance(char, top) <= 1) {
                    graph.addArc(currentNode to charToNode(this, row - 1, col))
                }
            }

            right?.run {
                if (climbDistance(char, right) <= 1) {
                    graph.addArc(currentNode to charToNode(this, row, col + 1))
                }
            }

            bottom?.run {
                if (climbDistance(char, bottom) <= 1) {
                    graph.addArc(currentNode to charToNode(this, row + 1, col))
                }
            }
        }
    }

    return graph
}

private fun climbDistance(a: Char, b: Char): Int {
    var start = a
    var end = b

    start = when (start) {
        'S' -> 'a'
        'E' -> 'z'
        else -> start
    }

    end = when (end) {
        'S' -> 'a'
        'E' -> 'z'
        else -> end
    }

    return end - start
}

private fun charToNode(char: Char, row: Int, col: Int): String = when (char) {
    'S' -> "S"
    'E' -> "E"
    else -> "$row/$col" // e.g. 0/1
}