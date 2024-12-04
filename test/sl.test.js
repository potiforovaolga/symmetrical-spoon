const { bot, slHandler } = require('./index');  // Импортируем бот и обработчик команды

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



