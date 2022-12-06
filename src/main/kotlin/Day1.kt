fun main() {

    val calories = mutableListOf<Int>()
    var sum = 0
    readFile("input/1.txt")
        .forEach {
            if (it.isBlank()) {
                calories.add(sum)
                sum = 0
            } else
                sum += Integer.parseInt(it)
        }

    calories.sortDescending()
    val result = calories.subList(0, 3).sum()

    print("result: $result")
}