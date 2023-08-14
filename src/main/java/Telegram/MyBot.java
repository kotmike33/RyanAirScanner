package Telegram;

import Config.ConfigVariables;
import DEBUG.Debug;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class MyBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

    }
    public void sendLinkToChannel(String messageText){
        Debug debug = new Debug();
        SendMessage message = new SendMessage();
        message.setChatId(ConfigVariables.CHANNEL_CHAT_ID);
        message.setText(messageText);
        message.setParseMode("Markdown");
        try {
            debug.functionDebug("Telegram send message job: " + messageText);
            execute(message);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessageToChannel(String messageText) throws IOException {
        Debug debug = new Debug();
        SendMessage message = new SendMessage();
                message.setChatId(ConfigVariables.CHANNEL_CHAT_ID);
                message.setText(messageText);
                message.setParseMode(ParseMode.MARKDOWNV2);
        try {
            debug.functionDebug("Telegram send message job: " + messageText);
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return "MyBot";
    }

    @Override
    public String getBotToken() {
        return ConfigVariables.TELEGRAM_BOT_TOKEN;
    }
}