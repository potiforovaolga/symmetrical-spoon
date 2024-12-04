
▎Задание 1

fun main() {
    val propertyOne: Float = 3.14f
    val propertyTwo: Float = 42.0f
    val result: Double = (propertyOne + propertyTwo).toDouble()
    println("Результат сложения: $result")
}


▎Задание 2

fun main() {
    val numberOne: Int = 10
    val numberTwo: Int = 5
    val result: Int = numberOne / numberTwo
    val remainder: Int = numberOne % numberTwo
    println("При делении $numberOne на $numberTwo результат равен $result, остаток равен $remainder")
    val wholePart: Int = result
    val fractionalPart: Int = remainder
    println("Результат деления $numberOne на $numberTwo равен $wholePart $fractionalPart/$numberTwo")
}


▎Задание 3

fun main() {
    val dayOfBirth: Int = 9
    val monthOfBirth: Int = 11
    val yearOfBirth: Int = 1980
    val currentDay: Int = 29
    val currentMonth: Int = 1
    val currentYear: Int = 2020
    val totalDaysInYear = 360
    val totalDaysInMonth = 30

    val yearsPassed: Int = currentYear - yearOfBirth
    val monthsPassed: Int = (currentMonth - monthOfBirth + yearsPassed * 12)
    val daysPassed: Int = (currentDay - dayOfBirth + monthsPassed * totalDaysInMonth)
    val secondsPassed: Long = daysPassed * 24 * 60 * 60
    println("$yearsPassed years, $monthsPassed months, $daysPassed days and $secondsPassed seconds have passed since my birth.")
    when (monthOfBirth) {
        in 1..3 -> println("Вы родились в первом квартале.")
        in 4..6 -> println("Вы родились во втором квартале.")
        in 7..9 -> println("Вы родились в третьем квартале.")
        in 10..12 -> println("Вы родились в четвертом квартале.")
    }
}


▎Задание 4

fun main() {
    // Вычисление синуса от числа 1 и округление до тысячных
    val sinValue = Math.sin(1.0)
    val roundedSinValue = String.format("%.3f", sinValue)
    
    println("Синус от числа 1, округленный до тысячных: $roundedSinValue")
}

