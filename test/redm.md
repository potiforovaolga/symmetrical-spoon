***Бот Telegram
Бот реализует следующие команды:
Доступные команды:
    /pow – возведение переданного числа в переданную степень
    /sl – сложение двух переданных чисел
    /wi – разность переданных чисел
    /cstr – подсчёт символов в переданной строке
    /auth <код_авторизации> - Аутентификация с ботом
    /register <имя_пользователя> - Регистрация с ботом
    /unknown - Обработка неизвестных команд
    /support - Получить поддержку
    /about - Получить информацию о боте
    /start - Запустить бота
    /help - Показать это сообщение справки

*** тесты для различных команд бота Telegram. 

1. **Тест для команды /auth:**
   - beforeEach: создается mock-реализация методов launch и stop для бота, а затем запускается бот.
   - it('should authenticate user with correct auth code'): Проверяет, что обработчик authHandler правильно аутентифицирует пользователя с корректным кодом.
   - it('should handle incorrect auth code'): Проверяет, что обработчик authHandler корректно обрабатывает неверный код аутентификации.
   - afterEach: Останавливает работу бота после выполнения тестов.

2. **Тест для команды /register:**
   - Аналогично первому тесту, но с тестами для регистрации пользователей.

3. **Тест для команды /unknown:**
   - Тест проверяет обработку неизвестной команды /unknown.

4. **Тест для команды /help:**
   - beforeEach: Создает mock-реализации методов launch и stop для бота, а затем запускает бот.
   - it('should return the list of commands'): Проверяет, что обработчик helpHandler возвращает список доступных команд.

5. **Команда /support:**
   - it('should provide support contact information'): Проверяет, что обработчик supportHandler возвращает контактную информацию для поддержки.
   - afterEach: Останавливает работу бота после выполнения тестов.

6. **Команда /about:**
   - it('should respond with information about the bot'): Проверяет, что обработчик aboutHandler возвращает информацию о боте.
   - afterEach: Останавливает работу бота после выполнения тестов.

7. **Команда /pow:**
   - it('should calculate the power of a number correctly'): Проверяет, что обработчик powHandler правильно вычисляет степень числа.
   - it('should prompt for correct usage if arguments are not provided correctly'): Проверяет, что обработчик powHandler корректно обрабатывает неверные аргументы.
   - afterEach: Останавливает работу бота после выполнения тестов.

8. **Команда /cstr:**
   - it('should count the number of characters in a string'): Проверяет, что обработчик cstrHandler правильно подсчитывает количество символов в строке.
   - it('should handle invalid arguments'): Проверяет, что обработчик cstrHandler корректно обрабатывает неверные аргументы.
   - afterEach: Останавливает работу бота после выполнения тестов.

9. **Команда /sl:**
   - it('should add two numbers together'): Проверяет, что обработчик slHandler правильно складывает два числа.
   - it('should handle invalid arguments'): Проверяет, что обработчик slHandler корректно обрабатывает неверные аргументы.
   - it('should handle non-numeric arguments'): Проверяет, что обработчик slHandler корректно обрабатывает нечисловые аргументы.

10. **Команда /wi:**
   - it('should calculate the difference between two numbers'): Проверяет, что обработчик wiHandler правильно считает разницу двух чисел.
   - it('should handle invalid arguments'): Проверяет, что обработчик wiHandler корректно обрабатывает неверные аргументы.
   - it('should handle non-numeric arguments'): Проверяет, что обработчик wiHandler корректно обрабатывает нечисловые аргументы.

