
import kotlin.random.Random

//  Создаем класс Employee
class Employee(
    val name: String,
    val surname: String,
    val salary: Int
)

 // Массивы имен и фамилий
val names = arrayOf("Дмитрий", "Вася", "Даниил", "Алексей", "Андрей")
val surnames = arrayOf("Васильев", "Пупкин", "Захаров", "Любимов", "Кузькин")

fun main() {
    //  Объявляем массив employees и заполняем его
    val employees = Array(10) {
        val randomName = names[Random.nextInt(names.size)]
        val randomSurname = surnames[Random.nextInt(surnames.size)]
        val randomSalary = Random.nextInt(1000, 2001)
        Employee(randomName, randomSurname, randomSalary)
    }

    //  Выводим информацию по каждому сотруднику
    for (employee in employees) {
        println("${employee.name} ${employee.surname}'s salary is $${employee.salary}")
    }

    //  Создаем массив с четными зарплатами
    val evenSalaryEmployees = employees.filter { it.salary % 2 == 0 }

    // Выводим информацию по сотрудникам с четной зарплатой
    println("nEmployees with even salaries:")
    for (employee in evenSalaryEmployees) {
        println("${employee.name} ${employee.surname}'s salary is $${employee.salary}")
    }
}


1. Класс Employee:

   • Имеет три свойства: name, surname и salary.

2. Массивы names и surnames:

   • Содержат имена и фамилии соответственно.

3. Создание массива employees:

   • Используется цикл для создания 10 объектов Employee.

   • Для имени и фамилии выбираются случайные значения из массивов.

   • Зарплата генерируется случайным образом в диапазоне от $1000 до $2000.

4. Вывод информации о каждом сотруднике:

   • Перебираем массив employees и выводим информацию о каждом сотруднике.

5. Фильтрация сотрудников с четными зарплатами:

   • Создается новый массив, который включает только тех сотрудников, чья зарплата четная.

   • Затем выводится информация о сотрудниках с четными зарплатами.

