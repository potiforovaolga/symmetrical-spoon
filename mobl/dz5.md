
#Задание 1

fun main() {
    val initialDeposit = 500000.0 // начальная сумма вклада
    val annualRate = 0.11 // процентная ставка
    val period = 5 // срок вклада в годах

    var totalAmount = initialDeposit
    for (year in 1..period) {
        totalAmount += totalAmount * annualRate // начисляем проценты
    }

    val profit = totalAmount - initialDeposit // вычисляем прибыль
    println("Сумма вклада через $period лет увеличится на ${profit.toInt()} и составит ${totalAmount.toInt()} рублей.")
}
// Сумма вклада через 5 лет увеличится на 342529 и составит 842529 рублей.



#Задание 2

fun main() {
    val numbers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    println("Четные числа из массива:")
    for (number in numbers) {
        if (number % 2 != 0) continue // Пропускаем нечетные числа
        println(number)
    }

    println("Нечетные числа из массива:")
    for (number in numbers) {
        if (number % 2 == 0) continue // Пропускаем четные числа
        println(number)
    }
}


#Задание 3

import kotlin.random.Random

fun main() {
    var iterations = 0

    while (true) {
        iterations++
        val randomNumber = Random.nextInt(1, 11) // случайное число от 1 до 10
        if (randomNumber == 5) {
            println("Чтобы выпало число 5 понадобилось $iterations итераций.")
            break // выходим из цикла

        }
    }
}
// Чтобы выпало число 5 понадобилось 27 итераций.


#Задание 4

fun main() {
    val height = 10 // высота столба в метрах
    var currentHeight = 0 // текущая высота черепашки
    var days = 0 // счетчик дней

    while (currentHeight < height) {
        days++
        currentHeight += 2 // за день черепашка поднимается на 2 метра
        if (currentHeight >= height) break // если достигла высоты, выходим из цикла
        currentHeight -= 1 // за ночь черепашка съезжает на 1 метр
    }

    println("Черепашка заберется на столб за $days дней.")
}
// Черепашка заберется на столб за 9 дней.
