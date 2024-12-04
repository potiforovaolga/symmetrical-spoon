
const { bot, unknownCommandHandler } = require('./index');  // Импортируем бот и обработчик команды

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
