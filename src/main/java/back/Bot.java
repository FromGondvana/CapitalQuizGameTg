package back;

import data.Sys;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private static Logger log;
    private Handler handler;

    public Bot()
    {
        log = Logger.getLogger(Bot.class);
        log.info("Create obj");

        handler = new Handler();
    }
    @Override
    public String getBotUsername() {
        return Sys.uname();
    }

    @Override
    public String getBotToken() {
        return Sys.token();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try{
            if(update.hasMessage() && update.getMessage().hasText()) {
                log.info("Get valid message");

                SendMessage sendMsg = new SendMessage();
                Response pack = handler.parse(update.getMessage());

                sendMsg.setChatId(update.getMessage().getChatId().toString());;
                sendMsg.setText(pack.getTxtQ());
                sendMsg.setReplyMarkup(pack.getKeyboard());

                /*DeleteMessage outMessage = new DeleteMessage();

                int messId = update.getMessage().getMessageId();
                outMessage.setMessageId(messId);
                outMessage.setChatId(update.getMessage().getChatId().toString());

                execute(outMessage);

                DeleteMessage outMessage1 = new DeleteMessage();


                outMessage1.setMessageId(messId - 1);
                outMessage1.setChatId(update.getMessage().getChatId().toString());

                execute(outMessage1);*/
                execute(sendMsg);
            }
        } catch (TelegramApiException e) {
            log.info(e);
            e.printStackTrace();
        }
    }
}
