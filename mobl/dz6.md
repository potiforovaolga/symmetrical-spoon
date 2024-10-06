
#Задание 1

fun main() {
    val gameResults = mapOf(
        "Салават Юлаев" to arrayOf("3:6", "5:5", "N/A"),
        "Авангард" to arrayOf("2:1"),
        "АкБарс" to arrayOf("3:3", "1:2")
    )

    for ((team, results) in gameResults) {
        for (result in results) {
            println("Игра с $team - $result")
        }
    }
}


#Задание 2

fun main() {
    val sum = calculateSum(1, 2, 3, 4, 5)
    println("Сумма чисел: $sum")
}

fun calculateSum(vararg numbers: Int): Int {
    return numbers.sum()
}


#▎Задание 3

// 3.1 Определяем, является ли число четным
fun isEven(number: Int): Boolean {
    return number % 2 == 0
}

// 3.2 Определяем, делится ли число на 3 без остатка
fun isDivisibleByThree(number: Int): Boolean {
    return number % 3 == 0
}

// 3.3 Возвращаем возрастающий массив чисел в интервале от x до y
fun generateUniqueNumbers(x: Int, y: Int): IntArray {
    return (x..y).toSet().toIntArray() // создаем уникальный массив
}

// 3.4 Создаем массив чисел от 1 до 100
fun createArrayFromOneToHundred(): IntArray {
    return generateUniqueNumbers(1, 100)
}

// 3.5 Фильтруем массив, возвращает новый массив без четных чисел
fun filterOutEvens(numbers: IntArray): IntArray {
    return numbers.filter { !isEven(it) }.toIntArray()
}

// 3.6 Фильтруем массив, возвращает новый массив без чисел кратных трем
fun filterOutMultiplesOfThree(numbers: IntArray): IntArray {
    return numbers.filter { !isDivisibleByThree(it) }.toIntArray()
}

// 3.7 Отфильтровываем массив от четных чисел и чисел кратных трем
fun main() {
    val numbers = createArrayFromOneToHundred()
    val filteredNumbers = filterOutMultiplesOfThree(filterOutEvens(numbers))

    println("Отфильтрованный массив: ${filteredNumbers.joinToString(", ")}")
}


