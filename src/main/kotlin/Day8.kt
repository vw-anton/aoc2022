fun main() {
    part1()
    part2()
}

private fun part1() {
    val trees = readFile("input/8.txt")
        .map { line -> line.toList().toTypedArray() }
        .toTypedArray()

    val size = trees.size
    val visible = Array(size) { i -> Array(size) { j -> 0 } }

    for (row in 1 until size - 1) {
        for (col in 1 until size - 1) {
            val height = trees.get(col, row)

            var visibleLeft = true
            for (left in 0 until col) {
                if (trees.get(row, left) >= height) {
                    visibleLeft = false
                    break
                }
            }

            var visibleRight = true
            for (right in col + 1 until size) {
                if (trees.get(row, right) >= height) {
                    visibleRight = false
                    break
                }
            }

            var visibleBottom = true
            for (bottom in row + 1 until size) {
                if (trees.get(bottom, col) >= height) {
                    visibleBottom = false
                    break
                }
            }

            var visibleTop = true
            for (top in 0 until row) {
                if (trees.get(top, col) >= height) {
                    visibleTop = false
                    break
                }
            }

            visible[col][row] = if (visibleLeft || visibleTop || visibleRight || visibleBottom) 1 else 0
        }
    }

    val outer = size * 4 - 4
    println("sum: ${visible.sumOf { array -> array.sum() } + outer}")
}

private fun Array<Array<Char>>.get(x: Int, y: Int, default: Int = -1): Int {
    return if ((x in indices) && (y in indices))
        get(x)[y].digitToInt()
    else
        default
}

fun part2() {
    val trees = readFile("input/8.txt")
        .map { it.toCharArray().map { char -> Tree(char.digitToInt()) } }

    val rowSize = trees.size
    val colSize = trees.size

    for (row in 1 until rowSize - 1) {
        for (col in 1 until colSize - 1) {
            val currentHeight = trees[row][col].height

            var leftScore = 0
            for (left in col - 1 downTo 0) {
                if (trees[row][left].height <= currentHeight) leftScore++
                if (trees[row][left].height >= currentHeight) break
            }

            var rightScore = 0
            for (right in col + 1 until colSize) {
                if (trees[row][right].height <= currentHeight) rightScore++
                if (trees[row][right].height >= currentHeight) break
            }

            var topScore = 0
            for (top in row - 1 downTo 0) {
                if (trees[top][col].height <= currentHeight) topScore++
                if (trees[top][col].height >= currentHeight) break
            }

            var bottomScore = 0
            for (top in row + 1 until rowSize) {
                if (trees[top][col].height <= currentHeight) bottomScore++
                if (trees[top][col].height >= currentHeight) break
            }

            val totalScore = leftScore * rightScore * topScore * bottomScore
            trees[row][col].score = totalScore
        }
    }

    val result = trees.maxOf { it.maxOf { tree -> tree.score } }
    println(result)
}

data class Tree(val height: Int, var score: Int = 0)