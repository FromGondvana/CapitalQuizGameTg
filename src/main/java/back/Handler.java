package back;

import data.Storage;
import data.Sys;
import front.Scene;
import keyboards.MainKeyboard;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class Handler {
    private static Logger log;
    private Sys sys;
    private Scene scene;
    private MainKeyboard mainKeys;
    private Storage storage;

    public Handler() {
        log = Logger.getLogger(Handler.class);
        log.info("Start constructor");

        storage = new Storage();
        storage.initLists();
        sys = new Sys();
        scene = new Scene(storage);

        mainKeys = new MainKeyboard();
        mainKeys.updateKeyboard();
    }

    public Response parse(Message inMsg) {
        Response response;
        String chatId = inMsg.getChatId().toString();
        String textMsg = inMsg.getText();
        String mainMenuLabel = "Приветствую вас. CapitalQuizBot - викторина по столицам мира. Подтяни себя в географии, " +
                "не будь глупым";


        if (textMsg.equals("/start")) {
            storage.addId(chatId);
            mainKeys.updateKeyboard();
            ReplyKeyboard keyboard;
            keyboard = mainKeys.getRepMarkup();

            response = new Response(chatId, mainMenuLabel, keyboard);
        }
        else if (textMsg.equals("Играть")) {

            storage.createSession(new GameSession(chatId, Sys.sizeList()));
            response = scene.initFirstGameMess(chatId, storage.getSession(chatId).getNextIndexQuestion());

        }
        else if (textMsg.equals("Инфо")) {
            String infoLabel = "По всем жалобам и предложениям пишите olkoswork@gmail.com";
            response = new Response(chatId, infoLabel, mainKeys.getRepMarkup());

        }
        else if (textMsg.equals("Статистика")) {

            String infoLabel = "Раздел в разработке";
            response = new Response(chatId, infoLabel, mainKeys.getRepMarkup());

        }
        else if(storage.isHasSessionWithId(chatId))
        {
            if(storage.getSession(chatId).isFinal())
            {
                storage.getSession(chatId).nextStep(scene.isCorrectAnswer(textMsg));
                response = scene.initFinalMessage(chatId, storage.getSession(chatId).getResult(), mainKeys.getRepMarkup());

                storage.deleteSession(chatId);
            }
            else if(textMsg.equals("Стоп"))
            {
                storage.deleteSession(chatId);
                response = new Response(chatId, "Игра принудительно закончена", mainKeys.getRepMarkup());
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
            response = new Response(chatId, "Неизвестная команда", mainKeys.getRepMarkup());
        }

        return response;
    }
}
