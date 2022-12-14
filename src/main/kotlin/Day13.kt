import com.beust.klaxon.JsonArray
import com.beust.klaxon.Parser
import java.lang.StringBuilder

fun main() {
    fun part1(input: List<String>) {
        val packets = input
            .splitBy { it.isEmpty() }
            .map { it.toPair() }
            .map { parse(it.first) to parse(it.second) }

        val sum = packets
            .mapIndexed { index, pair ->
                index + 1 to isInRightOrder(pair.first, pair.second)
            }
            .filter { it.second == -1 }
            .sumOf { it.first }

        println("part 1: $sum")
    }

    fun part2(input: List<String>) {
        val markers = listOf(
            parse("[[2]]"),
            parse("[[6]]")
        )

        val packets = input
            .filter { it.isNotEmpty() }
            .map { parse(it) }
            .toMutableList()

        packets += markers
        packets.sortWith(PackageComparator())

        val result = packets
            .mapIndexed { index, jsonArray ->
                when {
                    markers.contains(jsonArray) -> index + 1
                    else -> 0
                }
            }
            .filter { it != 0 }
            .fold(1) { acc, i -> acc * i }

        println("part 2: $result")
    }

    val file = readFile("input/13.txt")

    part1(file)
    part2(file)
}

private class PackageComparator() : Comparator<Any> {
    override fun compare(lhs: Any?, rhs: Any?): Int {
        return isInRightOrder(lhs, rhs)
    }
}

private fun parse(input: String): JsonArray<*> = Parser.default().parse(StringBuilder(input)) as JsonArray<*>

fun isInRightOrder(left: Any?, right: Any?): Int {
    return when {
        left is Int && right is Int -> {
            when {
                left < right -> -1
                left == right -> 0
                left > right -> 1
                else -> -1
            }
        }

        left is Int && right is JsonArray<*> -> isInRightOrder(JsonArray(left), right)
        left is JsonArray<*> && right is Int -> isInRightOrder(left, JsonArray(right))
        left is JsonArray<*> && right is JsonArray<*> -> {
            var index = 0
            while (index < left.size && index < right.size) {
                val result = isInRightOrder(left[index], right[index])
                if (result != 0)
                    return result

                index++
            }
            return if (left.size < right.size)
                -1
            else if (left.size == right.size)
                0
            else 1
        }

        else -> error("what happened")
    }
}

