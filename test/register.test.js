
const { bot, registerHandler } = require('./index'); // Импортируем бот и обработчик команды

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


