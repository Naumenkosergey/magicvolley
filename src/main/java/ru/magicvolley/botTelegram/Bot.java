package ru.magicvolley.botTelegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private static final String START = "/start";
    private static final String RESERVED = "/reserved";
    private static final String ANSWER = "/answer";

    public Bot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        var userName = update.getMessage().getChat().getUserName();
        switch (message) {
            case START -> startCommand(chatId, userName);
            case RESERVED -> reservedCommand(chatId, userName);
//            case ANSWER: -> setAnswer(chatId, "Отправить вопрос");
            default -> noneCommand(chatId);
        }
    }


    public void sendMessage(Long chatId, String text) {
        String chatIdStr = String.valueOf(chatId);
        SendMessage message = new SendMessage(chatIdStr, text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String campName, String userName, int bookingCount, String telephone) {

        var text = """
                %s хочет забронировать кемп %s на %d мест.
                Телефон для связи: %s
                """;
        String testFormat = String.format(text, userName, campName, bookingCount, telephone);
        String chatIdStr = String.valueOf(697146925);
        SendMessage message = new SendMessage(chatIdStr, testFormat);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    void startCommand(Long chatId, String userName) {
        var test = """
                Привет, %s я бот, который поможет тебе в работе с базой данных
                                
                """;
        String testFormat = String.format(test, userName);
        sendMessage(chatId, testFormat);
    }


    void reservedCommand(Long chatId, String userName) {

        var test = """
                пользователь %s забронировал хочет забронировать кемп 2
                                
                """;
        String testFormat = String.format(test, userName);
        sendMessage(chatId, testFormat);
    }

    void noneCommand(Long chatId) {

        var test = """
                команда не распознана
                                
                """;
        sendMessage(chatId, test);
    }

    @Override
    public String getBotUsername() {
        return "magicVolleyTest_bot";
    }
}

