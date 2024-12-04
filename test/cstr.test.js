
const { bot, cstrHandler } = require('./index');  // Импортируем бот и обработчик команды

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

