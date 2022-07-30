package back;

import data.Sessions;
import data.Sys;
import data.Users;
import front.Scene;
import keyboards.MainKeyboard;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class Handler {
    private static Logger log;
    private Users user;
    private Sys sys;
    private Scene scene;
    private Sessions sessions;
    private MainKeyboard mainKeys;

    public Handler() {
        log = Logger.getLogger(Handler.class);
        log.info("Start constructor");

        user = new Users();
        sys = new Sys();
        scene = new Scene();
        sessions = new Sessions();

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
            user.addId(chatId);
            mainKeys.updateKeyboard();
            ReplyKeyboard keyboard;
            keyboard = mainKeys.getRepMarkup();

            response = new Response(chatId, mainMenuLabel, keyboard);
        }
        else if (textMsg.equals("Играть")) {

            sessions.add(new GameSession(chatId, sys.size()));
            response = scene.initFirstGameMess(chatId, sessions.get(chatId));

        }
        else if (textMsg.equals("Инфо")) {
            String infoLabel = "По всем жалобам и предложениям пишите olkoswork@gmail.com";
            response = new Response(chatId, infoLabel, mainKeys.getRepMarkup());

        }
        else if (textMsg.equals("Статистика")) {

            String infoLabel = "Раздел в разработке";
            response = new Response(chatId, infoLabel, mainKeys.getRepMarkup());

        }
        else if(sessions.isHasWithId(chatId))
        {
            if(sessions.get(chatId).isFinal())
            {
                sessions.get(chatId).nextStep(scene.isCorrectAnswer(textMsg));
                response = scene.initFinalMessage(chatId, sessions.get(chatId).getResult(), mainKeys.getRepMarkup());

                sessions.delete(chatId);
            }
            else if(textMsg.equals("Стоп"))
            {
                sessions.delete(chatId);
                response = new Response(chatId, "Игра принудительно закончена", mainKeys.getRepMarkup());
            }
            else {

                String respTxt;
                if (scene.isCorrectAnswer(textMsg)) {
                    respTxt = "Верный ответ\n\n";
                } else {
                    respTxt = "Неверный ответ\n\n";
                }

                sessions.get(chatId).nextStep(scene.isCorrectAnswer(textMsg));

                response = scene.initMessageQuestion(chatId, sessions.get(chatId));
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
