#Задача 1: Задание по кортежам

В Kotlin нет встроенной поддержки кортежей, можно использовать data class.

data class PersonInfo(val name: String, val age: Int, val favoriteNumber: Int)

fun main() {
    // Создаем экземпляр класса с данными
    val person = PersonInfo("Ольга", 36, 19)

    // Выводим каждый элемент на консоль
    println("Имя: ${person.name}")
    println("Возраст: ${person.age}")
    println("Любимое число: ${person.favoriteNumber}")
}


#Задача 2: Задача по логике

fun main() {
    // Исходные данные
    val hasTicket = true // У пользователя есть билет
    val isAdult = true   // Пользователь старше 18 лет
    val isRegistered = false // Пользователь не зарегистрирован
    val isVIP = false // Пользователь не VIP

    // Логическое выражение для проверки доступа
    val canAccess = (hasTicket && (isAdult && (isRegistered || isVIP)))

    // Выводим результат
    if (canAccess) {
        println("Доступ разрешен на конференцию.")
    } else {
        println("Доступ запрещен на конференцию.")
    }
}

