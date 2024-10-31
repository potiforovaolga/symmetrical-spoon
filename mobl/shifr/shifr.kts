import java.math.BigInteger // Импортируем класс BigInteger для работы с большими целыми числами
import java.security.SecureRandom // Импортируем класс SecureRandom для генерации случайных чисел

// Определяем класс RSA для реализации алгоритма шифрования RSA
class RSA(bitLength: Int) {
    private val p: BigInteger // Простое число p
    private val q: BigInteger // Простое число q
    val n: BigInteger // Модуль n = p * q, используется в шифровании и расшифровке
    private val phi: BigInteger // Значение phi(n) = (p - 1)(q - 1), используется для вычисления d
    val e: BigInteger // Открытая экспонента e
    val d: BigInteger // Закрытая экспонента d

    init {
        val random = SecureRandom() // Создаем объект SecureRandom для генерации случайных чисел
        // Генерируем два больших простых числа p и q
        p = BigInteger.probablePrime(bitLength / 2, random) // Генерация простого числа p
        q = BigInteger.probablePrime(bitLength / 2, random) // Генерация простого числа q
        
        // Вычисляем n = p * q
        n = p.multiply(q) // Умножаем p и q для получения модуля n
        
        // Вычисляем phi(n) = (p - 1)(q - 1)
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)) // Вычисляем значениеphi(n)
        
        // Выбираем e (обычно 65537)
        e = BigInteger("65537") // Устанавливаем значение открытой экспоненты e
        
        // Находим d как обратное к e по модулю φ(n)
        d = e.modInverse(phi) // Вычисляем закрытую экспоненту d как обратное значение к e по модулю φ(n)
    }

    // Метод для шифрования сообщения
    fun encrypt(message: BigInteger): BigInteger {
        return message.modPow(e, n) // Возвращаем зашифрованное сообщение, используя модульное возведение в степень
    }

    // Метод для расшифровки сообщения
    fun decrypt(encrypted: BigInteger): BigInteger {
        return encrypted.modPow(d, n) // Возвращаем расшифрованное сообщение, используя модульное возведение в степень
    }

    // Метод для преобразования строки в BigInteger
    fun stringToBigInteger(message: String): BigInteger {
        val bytes = message.toByteArray(Charsets.UTF_8) // Преобразуем строку в массив байтов
        return BigInteger(bytes) // Преобразуем массив байтов в BigInteger
    }

    // Метод для преобразования BigInteger в строку
    fun bigIntegerToString(value: BigInteger): String {
        val bytes = value.toByteArray() // Преобразуем BigInteger в массив байтов
        return String(bytes, Charsets.UTF_8) // Преобразуем массив байтов обратно в строку
    }
}

fun main() {
    val bitLength = 128 // Длина ключа в битах
    val rsa = RSA(bitLength) // Создаем экземпляр класса RSA с заданной длиной ключа

    println("Введите сообщение для шифрования: ") // Запрашиваем у пользователя ввод сообщения
    val inputMessage = readLine() ?: "" // Читаем введенное сообщение (или пустую строку, если ничего не введено)

    val messageBigInt = rsa.stringToBigInteger(inputMessage) // Преобразуем введенное сообщение в BigInteger
    println("Оригинальное сообщение: $inputMessage") // Выводим оригинальное сообщение

    // Шифрование
    val encrypted = rsa.encrypt(messageBigInt) // Шифруем сообщение
    println("Зашифрованное сообщение: $encrypted") // Выводим зашифрованное сообщение

    // Дешифрование
    val decryptedBigInt = rsa.decrypt(encrypted) // Дешифруем зашифрованное сообщение
    val decryptedMessage = rsa.bigIntegerToString(decryptedBigInt) // Преобразуем обратно в строку
    println("Расшифрованное сообщение: $decryptedMessage") // Выводим расшифрованное сообщение
}
