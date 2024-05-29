
const { bot, powHandler } = require('./index'); // Импортируем бот и обработчик команды

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
