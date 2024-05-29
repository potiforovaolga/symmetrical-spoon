
const { bot, aboutHandler } = require('./index');  // Импортируем бот и обработчик команды

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


