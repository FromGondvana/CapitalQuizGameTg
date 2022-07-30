package back;

import data.Sessions;
import data.Sys;
import data.Users;
import front.Scene;
import keyboards.MainKeyboard;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class Handler {
    private static Logger log;
    private Users user;
    private Sys sys;
    private Scene scene;
    private Sessions sessions;
    private MainKeyboard mainKeys;
    //private ReplyKeyboard keyboard;

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

    public PackValue parse(Message inMsg) {
        log.info("Call parse");
        PackValue packValue;
        String chatId = inMsg.getChatId().toString();
        String textMsg = inMsg.getText();
        String mainMenuLabel = "Приветствую вас. CapitalQuizBot - викторина по столицам мира. Подтяни себя в географии, " +
                "не будь глупым";


        if (textMsg.equals("/start")) {
            log.info("Parse \"/start\" started");
            user.addId(chatId);
            mainKeys.updateKeyboard();
            ReplyKeyboard keyboard;
            keyboard = mainKeys.getRepMarkup();

            packValue = new PackValue(chatId, mainMenuLabel, keyboard);
        }
        else if (textMsg.equals("Играть")) {
            log.info("Parse \"Играть\" started");

            sessions.add(new GameSession(chatId, sys.size()));
            //ReplyKeyboard keyboard = gameScene.keyboard();
            packValue = scene.initFirstGameMess(chatId);

        }
        else if(sessions.isHasWithId(chatId))
        {
            if(sessions.get(chatId).isFinal())
            {
                sessions.get(chatId).nextStep(scene.isCorrectAnswer(textMsg));
                packValue = scene.initFinalMessage(chatId, sessions.get(chatId).getResult(), mainKeys.getRepMarkup());

                sessions.delete(chatId);
            }
            else {

                String respTxt;
                if (scene.isCorrectAnswer(textMsg)) {
                    respTxt = "Это правильный ответ\n";
                } else {
                    respTxt = "Это неправильный ответ\n";
                }

                sessions.get(chatId).nextStep(scene.isCorrectAnswer(textMsg));

                packValue = scene.initMessageQuestion(chatId, sessions.get(chatId).getRoundNumber());
                respTxt = respTxt.concat(packValue.getTxtQ());
                packValue.setTxtQ(respTxt);
            }
        }
        else
        {
            packValue = new PackValue(chatId, "Неизвестная команда", mainKeys.getRepMarkup());
        }

        /*else
        {
            packValue = new PackValue(chatId, "Неизвестная команда", mainKeys.getRepMarkup());
        }*/
        /*else{
            String responseText = scene.initRespMess(textMsg);
            sessions.get(chatId).nextStep(scene.isCorrectAnswer(textMsg));

            if(sessions.get(chatId).isPreFinal())
            {

            }
            packValue = scene.initMessageQuestion(chatId);
            packValue.setTxtQ(responseText.concat(packValue.getTxtQ()));

            //System.out.println(userData.getPlPlays(chatId).getIndex() + " " + gameScene.countQuestion);
            //System.out.println(userData.getPlPlays(chatId).getIndex() == gameScene.countQuestion);

            if(usersData.getSession(chatId).getIndex() == gameScene.countQuestion);
            {
                packValue.setTxtQ("Ваш результат ".concat(Integer.toString(usersData.getSession(chatId).getResult())));
                packValue.setKeyboard(mainKeys.getRepMarkup());
            }
        }*/

        return packValue;
    }
}
