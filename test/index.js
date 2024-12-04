const { Telegraf } = require('telegraf')

const TOKEN = 'your token'
const bot = new Telegraf(TOKEN)

bot.start((ctx) => ctx.reply('Добро пожаловать'))

// Команда для аутентификации
bot.command('auth', (ctx) => {
    const authCode = ctx.message.text.split('/auth ')[1];
    if (true) {
        ctx.reply('Аутентификация прошла успешно! Введенный код: ${authCode}');
    } else {
        ctx.reply('Неверный код авторизации. Пожалуйста, попробуйте еще раз.');
    }
})

// Команда для регистрации
bot.command('register', (ctx) => {
    const userName = ctx.message.text.split('/register ')[1];
    if (userName) {
        ctx.reply('Пользователь ${userName} успешно зарегистрирован!');
    } else {
        ctx.reply('Неверный формат регистрации. Пожалуйста, используйте /register <имя_пользователя>');
    }
})

// Команда для обработки неизвестных команд
bot.command('unknown', (ctx) => {
    ctx.reply('Неизвестная команда. Пожалуйста, используйте /help для списка доступных команд.');
})

// Команда для помощи
bot.command('help', (ctx) => {
    const helpMessage = `Доступные команды:
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
    /help - Показать это сообщение справки`;
    ctx.reply(helpMessage);
})

// Команда для получения поддержки
bot.command('support', (ctx) => {
    ctx.reply('Пожалуйста, свяжитесь с нами по адресу potiforovaolga@gmail.com для любых проблем с поддержкой.');
})

// Команда для получения информации о боте
bot.command('about', (ctx) => {
    ctx.reply('Этот бот создан для того чтобы сдать зачет!');
})

// Команда для возведения числа в степень
bot.command('pow', (ctx) => {
    const args = ctx.message.text.split(' ');
    if (args.length !== 3) {
        ctx.reply('Используйте команду /pow [число] [степень]');
    } else {
        const number = parseFloat(args[1]);
        const power = parseFloat(args[2]);
        const result = Math.pow(number, power);
        ctx.reply(result);
    }
});

// Команда для подсчёта символов в строке
bot.command('cstr', (ctx) => {
    const args = ctx.message.text.split(' ');
    if (args.length !== 2) {
        ctx.reply('Используйте команду /cstr [строка]');
    } else {
        const text = args[1];
        const count = text.length;
        ctx.reply(count);
    }
});

// Команда для сложения двух чисел
bot.command('sl', (ctx) => {
    const args = ctx.message.text.split(' ');
    if (args.length !== 3) {
        ctx.reply('Используйте команду /sl [число1] [число2]');
    } else {
        const num1 = parseFloat(args[1]);
        const num2 = parseFloat(args[2]);
        const sum = num1 + num2;
        ctx.reply(sum);
    }
});

// Команда для разницы двух чисел
bot.command('wi', (ctx) => {
    const args = ctx.message.text.split(' ');
    if (args.length !== 3) {
        ctx.reply('Используйте команду /wi [число1] [число2]');
    } else {
        const num1 = parseFloat(args[1]);
        const num2 = parseFloat(args[2]);
        const diff = num1 - num2;
        ctx.reply(diff);
    }
});

bot.launch()
