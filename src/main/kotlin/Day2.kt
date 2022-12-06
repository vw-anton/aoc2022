fun main() {
    val pairs = readFile("input/2.txt")
        .map { line -> line.split(" ") }
        .map { chars -> mapItem(chars.first()) to mapResult(chars.last()) }


    val items = pairs.map { pair -> chooseItem(pair) }.sumOf {
        it.points
    }

    val results = pairs.sumOf { pair -> pair.second.points }

    println(results + items)
}

fun chooseItem(pair: Pair<Item, Result>): Item {
    if (pair.second == Result.Draw)
        return pair.first

    if (pair.second == Result.Win) {
        return when (pair.first) {
            Item.Scissor -> Item.Rock
            Item.Rock -> Item.Paper
            Item.Paper -> Item.Scissor
            else -> Item.Invalid
        }
    }

    if (pair.second == Result.Loss) {
        return when (pair.first) {
            Item.Scissor -> Item.Paper
            Item.Rock -> Item.Scissor
            Item.Paper -> Item.Rock
            else -> Item.Invalid
        }
    }

    return Item.Invalid
}

fun chooseWinner(pair: Pair<Item, Item>): Result {
    if (pair.first == pair.second)
        return Result.Draw

    if (pair.second == Item.Rock) {
        return if (pair.first == Item.Paper)
            Result.Loss
        else
            Result.Win
    }

    if (pair.second == Item.Paper) {
        return if (pair.first == Item.Scissor)
            Result.Loss
        else
            Result.Win
    }

    if (pair.second == Item.Scissor) {
        return if (pair.first == Item.Rock)
            Result.Loss
        else
            Result.Win
    }

    return Result.Invalid
}


enum class Result(val points: Int) {
    Draw(3), Win(6), Loss(0), Invalid(1000)
}

enum class Item(val points: Int) {
    Scissor(3), Paper(2), Rock(1), Invalid(0)
}

fun mapItem(s: String): Item {
    return when (s) {
        "A" -> Item.Rock
        "B" -> Item.Paper
        "C" -> Item.Scissor
        else -> Item.Invalid
    }
}

fun mapResult(s: String): Result {
    return when (s) {
        "X" -> Result.Loss
        "Y" -> Result.Draw
        "Z" -> Result.Win
        else -> Result.Invalid
    }
}


