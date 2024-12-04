
const { bot, supportHandler } = require('./index');  // Импортируем бот и обработчик команды

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

