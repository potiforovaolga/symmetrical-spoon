// Для команды /help, 
const { bot, helpHandler } = require('./index');  // Импортируем бот и обработчик команды

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


