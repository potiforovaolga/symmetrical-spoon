** Импортируем необходимые библиотеки
import { Telegraf } from 'telegraf';
import axios from 'axios';

* Создаем экземпляр бота с указанием токена
const bot = new Telegraf('ТОКЕН');


  ** Тест для команды /auth
describe('/auth command', () => {
    beforeEach(() => {
        jest.spyOn(bot, 'launch').mockImplementation(() => {});
        jest.spyOn(bot, 'stop').mockImplementation(() => {});
        bot.launch();
    });

    it('should authenticate user with correct auth code', () => {
        const ctx = {
            message: {
                text: '/auth 12345'
            },
            reply: jest.fn()
        };

        authHandler(ctx);
        expect(ctx.reply).toHaveBeenCalledWith('Аутентификация прошла успешно! Введенный код: 12345');
    });

    it('should handle incorrect auth code', () => {
        const ctx = {
            message: {
                text: '/auth 54321'
            },
            reply: jest.fn()
        };

        authHandler(ctx);
        expect(ctx.reply).toHaveBeenCalledWith('Неверный код авторизации. Пожалуйста, попробуйте еще раз.');
    });

    afterEach(() => {
        bot.stop('test');
    });
});

** Для команды /register 
describe('/register command', () => {
    beforeEach(() => {
        jest.spyOn(bot, 'launch').mockImplementation(() => {});
        jest.spyOn(bot, 'stop').mockImplementation(() => {});
        bot.launch();
    });

    it('should register user with correct username', () => {
        const ctx = {
            message: {
                text: '/register JohnDoe'
            },
            session: {},
            reply: jest.fn()
        };

        registerHandler(ctx);
        expect(ctx.reply).toHaveBeenCalledWith('Пользователь JohnDoe успешно зарегистрирован!');
    });

    it('should prompt for username if none is provided', () => {
        const ctx = {
            message: {
                text: '/register'
            },
            session: {},
            reply: jest.fn()
        };

        registerHandler(ctx);
        expect(ctx.reply).toHaveBeenCalledWith('Неверный формат регистрации. Пожалуйста, используйте /register <имя_пользователя>');
    });

    afterEach(() => {
        bot.stop('test');
    });
});

** Для команды /unknown, 
describe('/unknown command', () => {
    beforeEach(() => {
        jest.spyOn(bot, 'launch').mockImplementation(() => {});
        jest.spyOn(bot, 'stop').mockImplementation(() => {});
        bot.launch();
    });

    it('should handle unknown command', () => {
        const ctx = {
            message: {
                text: '/unknown'
            },
            reply: jest.fn()
        };

        unknownCommandHandler(ctx);
        expect(ctx.reply).toHaveBeenCalledWith('Неизвестная команда. Пожалуйста, используйте /help для списка доступных команд.');
    });

    afterEach(() => {
        bot.stop('test');
    });
});

** Для команды /help, 
describe('/help command', () => {
    beforeEach(() => {
        jest.spyOn(bot, 'launch').mockImplementation(() => {});
        jest.spyOn(bot, 'stop').mockImplementation(() => {});
        bot.launch();
    });

    it('should return the list of commands', () => {
        const ctx = {
            reply: jest.fn()
        };

        helpHandler(ctx);

        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/pow – возведение переданного числа в переданную степень'));
        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/sl – сложение двух переданных чисел'));
        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/wi – разность переданных чисел'));
        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/cstr – подсчёт символов в переданной строке'));
        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/auth <код_авторизации> - Аутентификация с ботом'));
        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/register <имя_пользователя> - Регистрация с ботом'));
        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/unknown - Обработка неизвестных команд'));
        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/support - Получить поддержку'));
        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/about - Получить информацию о боте'));
        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/start - Запустить бота'));
        expect(ctx.reply).toHaveBeenCalledWith(expect.stringContaining('/help - Показать это сообщение справки`;а'));
    });

    afterEach(() => {
        bot.stop('test');
    });
});


** Для команды /support, 
describe('/support command', () => {
    beforeEach(() => {
        jest.spyOn(bot, 'launch').mockImplementation(() => {});
        jest.spyOn(bot, 'stop').mockImplementation(() => {});
        bot.launch();
    });

    it('should provide support contact information', () => {
        const ctx = {
            message: {
                text: '/support'
            },
            reply: jest.fn()
        };

        supportHandler(ctx);
        expect(ctx.reply).toHaveBeenCalledWith('Пожалуйста, свяжитесь с нами по адресу potiforovaolga@gmail.com для любых проблем с поддержкой.');
    });

    afterEach(() => {
        bot.stop('test');
    });
});

** Для команды /about)
describe('/about command', () => {
    beforeEach(() => {
        jest.spyOn(bot, 'launch').mockImplementation(() => {});
        jest.spyOn(bot, 'stop').mockImplementation(() => {});
        bot.launch();
    });

    it('should respond with information about the bot', () => {
        const ctx = {
            reply: jest.fn()
        };

        aboutHandler(ctx);

        expect(ctx.reply).toHaveBeenCalledWith('Этот бот создан для того чтобы сдать зачет!');
    });

    afterEach(() => {
        bot.stop('test');
    });
});

** Для команды '/pow 
describe('/pow command', () => {
    beforeEach(() => {
        jest.spyOn(bot, 'launch').mockImplementation(() => {});
        jest.spyOn(bot, 'stop').mockImplementation(() => {});
        bot.launch();
    });

    it('should calculate the power of a number correctly', () => {
        const ctx = {
            message: {
                text: '/pow 2 3'
            },
            reply: jest.fn()
        };

        powHandler(ctx);
        expect(ctx.reply).toHaveBeenCalledWith(8);
    });

    it('should prompt for correct usage if arguments are not provided correctly', () => {
        const ctx = {
            message: {
                text: '/pow 2'
            },
            reply: jest.fn()
        };

        powHandler(ctx);
        expect(ctx.reply).toHaveBeenCalledWith('Используйте команду /pow [число] [степень]');
    });

    afterEach(() => {
        bot.stop('test');
    });
});

** Для команды /cstr 
describe('/cstr command', () => {
    beforeEach(() => {
        jest.spyOn(bot, 'launch').mockImplementation(() => {});
        jest.spyOn(bot, 'stop').mockImplementation(() => {});
        bot.launch();
    });

    it('should count the number of characters in a string', () => {
        const ctx = {
            message: {
                text: '/cstr Hello'
            },
            reply: jest.fn()
        };

        cstrHandler(ctx);

        expect(ctx.reply).toHaveBeenCalledWith(5);
    });

    it('should handle invalid arguments', () => {
        const ctx = {
            message: {
                text: '/cstr'
            },
            reply: jest.fn()
        };

        cstrHandler(ctx);

        expect(ctx.reply).toHaveBeenCalledWith('Используйте команду /cstr [строка]');
    });

    afterEach(() => {
        bot.stop('test');
    });
});

** Для команды /sl 
describe('/sl command', () => {
    beforeEach(() => {
        jest.spyOn(bot, 'launch').mockImplementation(() => {});
        jest.spyOn(bot, 'stop').mockImplementation(() => {});
        bot.launch();
    });

    it('should add two numbers together', () => {
        const ctx = {
            message: {
                text: '/sl 5 10'
            },
            reply: jest.fn()
        };

        slHandler(ctx);

        expect(ctx.reply).toHaveBeenCalledWith(15);
    });

    it('should handle invalid arguments', () => {
        const ctx = {
            message: {
                text: '/sl'
            },
            reply: jest.fn()
        };

        slHandler(ctx);

        expect(ctx.reply).toHaveBeenCalledWith('Используйте команду /sl [число1] [число2]');
    });

    it('should handle non-numeric arguments', () => {
        const ctx = {
            message: {
                text: '/sl a b'
            },
            reply: jest.fn()
        };

        slHandler(ctx);

        expect(ctx.reply).toHaveBeenCalledWith('Используйте команду /sl [число1] [число2]');
    });

    afterEach(() => {
        bot.stop('test');
    });
});

** для команды /wi 
describe('/wi command', () => {
    beforeEach(() => {
        jest.spyOn(bot, 'launch').mockImplementation(() => {});
        jest.spyOn(bot, 'stop').mockImplementation(() => {});
        bot.launch();
    });

    it('should calculate the difference between two numbers', () => {
        const ctx = {
            message: {
                text: '/wi 5 3'
            },
            reply: jest.fn()
        };

        wiHandler(ctx);

        expect(ctx.reply).toHaveBeenCalledWith(2);
    });

    it('should handle invalid arguments', () => {
        const ctx = {
            message: {
                text: '/wi 5'
            },
            reply: jest.fn()
        };

        wiHandler(ctx);

        expect(ctx.reply).toHaveBeenCalledWith('Используйте команду /wi [число1] [число2]');
    });

    it('should handle non-numeric arguments', () => {
        const ctx = {
            message: {
                text: '/wi 5 a'
            },
            reply: jest.fn()
        };

        wiHandler(ctx);

        expect(ctx.reply).toHaveBeenCalledWith('Используйте команду /wi [число1] [число2]');
    });

    afterEach(() => {
        bot.stop('test');
    });
});




