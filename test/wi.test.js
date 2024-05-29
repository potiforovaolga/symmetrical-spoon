
const { bot, wiHandler } = require('./index');  // Импортируем бот и обработчик команды

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


