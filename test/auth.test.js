
const { bot, authHandler } = require('./index'); // Импортируем бот и обработчик команды

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

