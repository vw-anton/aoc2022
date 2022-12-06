fun main() {
    val input = readFile("input/4.txt")
        .map { it.split(",").toPair() }
        .map { it.first.toIntRange() to it.second.toIntRange() }

    // In how many assignment pairs does one range fully contain the other?
    val part1 = input
        .count { pair ->
            pair.first.fullyContains(pair.second)
                    || pair.second.fullyContains(pair.first)
        }

    // In how many assignment pairs do the ranges overlap?
    val part2 = input
        .count { pair ->
            pair.first.overlaps(pair.second)
                    || pair.second.overlaps(pair.first)
        }

    println("part1: $part1")
    println("part2: $part2")
}

fun String.toIntRange(): IntRange {
    val split = this.split("-")
    return split.first().toInt()..split.last().toInt()
}

fun IntRange.fullyContains(other: IntRange): Boolean {
    return contains(other.first) && contains(other.last)
}

fun IntRange.overlaps(other: IntRange): Boolean {
    return contains(other.first) || contains(other.last)
}