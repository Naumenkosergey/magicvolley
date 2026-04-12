package ru.magicvolley.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.magicvolley.botTelegram.Bot;

@Configuration
@ConditionalOnProperty(name = "telegram.bot.enabled", havingValue = "true")
public class BotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(Bot bot) throws TelegramApiException {

        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
        return api;
    }
}
