package back;

import data.Storage;
import data.Sys;
import front.Keyboard;
import front.Scene;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class Handler {
    private static Logger log;
    private Sys sys;
    private Scene scene;
    private Keyboard keyboard;
    private Storage storage;

    public Handler() {
        log = Logger.getLogger(Handler.class);
        log.info("Start constructor");

        storage = new Storage();
        storage.initLists();
        sys = new Sys();
        scene = new Scene(storage);

        keyboard = new Keyboard();
        keyboard.initMainKeyboard();
        keyboard.updateKeyboard();
    }

    public Response parse(Message inMsg) {
        Response response;
        String chatId = inMsg.getChatId().toString();
        String textMsg = inMsg.getText();
        String mainMenuLabel = "Приветствую вас. CapitalQuizBot - викторина по столицам мира. Подтяни себя в географии, " +
                "не будь глупым";


        if (textMsg.equals("/start")) {
            storage.addId(chatId);
            keyboard.updateKeyboard();
            ReplyKeyboard replyKeyboard;
            replyKeyboard = keyboard.getMarkup();

            response = new Response(chatId, mainMenuLabel, replyKeyboard);
        }
        else if (textMsg.equals("Играть")) {

            storage.createSession(new GameSession(chatId, Sys.sizeList()));
            response = scene.initFirstGameMess(chatId, storage.getSession(chatId).getNextIndexQuestion());

        }
        else if (textMsg.equals("Инфо")) {
            String infoLabel = "По всем жалобам и предложениям пишите olkoswork@gmail.com";
            response = new Response(chatId, infoLabel, keyboard.getMarkup());

        }
        else if (textMsg.equals("Статистика")) {

            String infoLabel = "Раздел в разработке";
            response = new Response(chatId, infoLabel, keyboard.getMarkup());

        }
        else if(storage.isHasSessionWithId(chatId))
        {
            if(storage.getSession(chatId).isFinal())
            {
                storage.getSession(chatId).nextStep(scene.isCorrectAnswer(textMsg));
                response = scene.initFinalMessage(chatId, storage.getSession(chatId).getResult(), keyboard.getMarkup());

                storage.deleteSession(chatId);
            }
            else if(textMsg.equals("Стоп"))
            {
                storage.deleteSession(chatId);
                keyboard.initMainKeyboard();
                keyboard.updateKeyboard();
                response = new Response(chatId, "Игра принудительно закончена", keyboard.getMarkup());
            }
            else {

                String respTxt;
                if (scene.isCorrectAnswer(textMsg)) {
                    respTxt = "Верный ответ\n\n";
                } else {
                    respTxt = "Неверный ответ\n\n";
                }

                storage.getSession(chatId).nextStep(scene.isCorrectAnswer(textMsg));

                response = scene.initMessageQuestion(chatId, storage.getSession(chatId).getRoundNumber(), storage.getSession(chatId).getNextIndexQuestion());
                respTxt = respTxt.concat(response.getTxtQ());
                response.setTxtQ(respTxt);
            }
        }
        else
        {
            response = new Response(chatId, "Неизвестная команда", keyboard.getMarkup());
        }

        return response;
    }
}
