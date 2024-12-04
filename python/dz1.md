    // Задание 1
    val firstString = "I can"
    val secondString = "code"
    println("$firstString $secondString!")  // Вывод: I can code!

    // Задание 2
    val myAge = 25  // Укажите ваш возраст
    val myAgeInTenYears = myAge + 10
    val daysInYear = 365.25
    val daysPassed: Float = (myAgeInTenYears * daysInYear).toFloat()

    println("Мой возраст $myAge лет. Через 10 лет, мне будет $myAgeInTenYears лет, с момента моего рождения пройдет $daysPassed дней.")

    // Задание 3
    val cathetusAC = 8.0
    val cathetusCB = 6.0

    // Вычисление гипотенузы AB
    val hypotenuseAB = Math.sqrt(cathetusAC * cathetusAC + cathetusCB * cathetusCB)

    // Вычисление площади и периметра
    val area = (cathetusAC * cathetusCB) / 2
    val perimeter = cathetusAC + cathetusCB + hypotenuseAB

    println("Площадь прямоугольного треугольника: $area")
    println("Периметр прямоугольного треугольника: $perimeter")
}


▎Объяснение кода:

1. Задание 1:
Объявлены константы firstString и secondString.
Выводится строка с использованием интерполяции.

2. Задание 2:
Объявлено свойство myAge, которому присвоено значение вашего возраста.
Вычисляется myAgeInTenYears, daysInYear и daysPassed.
Результаты выводятся на консоль с использованием интерполяции.

3. Задание 3:
Объявлены катеты cathetusAC и cathetusCB.
Вычисляется гипотенуза с помощью функции sqrt.
Вычисляются площадь и периметр прямоугольного треугольника.
Результаты выводятся на консоль.

