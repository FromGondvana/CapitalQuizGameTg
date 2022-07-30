package front;

import back.Response;
import data.Storage;
import data.Sys;
import keyboards.GameKeyboard;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private Logger log;
    private GameKeyboard gameKeyboard;
    private Storage gameData;
    private String buffAnswer;
    private List<String> buffChoises;
    private int countQuestion;

    public Scene()
    {
        log = Logger.getLogger(Scene.class);
        log.info("Starting constructor");
        gameKeyboard = new GameKeyboard();
        gameData = new Storage();
        gameData.initLists();
        countQuestion = gameData.getSizeQList();
        buffChoises = new ArrayList<>();
    }

    public Response initFirstGameMess(String id, int indexQue)
    {
        log.info("Starting metod with id ".concat(id));
        Response response;

        response = initMessageQuestion(id, 1, indexQue);

        return response;
    }

    public Response initMessageQuestion(String id, int roundNum, int indexQue)
    {
        log.info("Starting metod with id ".concat(id));
        Response response;

        List<String> choiseStrList = new ArrayList<>();
        List<Integer> choiseIndList = new ArrayList<>();
        String correctAns;
        String question = "Вопрос №".concat(Integer.toString(roundNum)).concat("\n");
        int size = Sys.size();
        choiseIndList.add(indexQue);
        choiseStrList.add(gameData.getQuestion(indexQue).getAnswer());
        correctAns = gameData.getQuestion(indexQue).getAnswer();
        question = question.concat(gameData.getQuestion(indexQue).getQuestion());

        buffChoises.clear();
        buffChoises.addAll(choiseStrList);
        buffAnswer = correctAns;

        int choiseBuff;
        for(int i = 0; i < 3; i++)
        {
            do {
                choiseBuff = (int) (Math.random() * size);
            }while (choiseIndList.contains(choiseBuff));

            choiseStrList.add(gameData.getQuestion(choiseBuff).getAnswer());
            choiseIndList.add(choiseBuff);
        }

        gameKeyboard.initKeyboardRows(choiseStrList);
        gameKeyboard.updateKeyboard();
        ReplyKeyboardMarkup keyboard = this.gameKeyboard.getKeyboardMarkup();

        response = new Response(id, question, correctAns, keyboard);

        return response;
    }

    public Response initFinalMessage(String id, int result, ReplyKeyboardMarkup markup)
    {
        log.info("Starting metod with id ".concat(id));
        Response response;
        String responseTxt;

        buffChoises.clear();

        responseTxt = "Вопросы закончились\n"
                .concat(Integer.toString(result))
                .concat(" - количество правильных ответов\n")
                .concat(Double.toString(((double) result) / ((double) Sys.size()) * 100))
                .concat("% - ваш результат. Попробуем еще?");
        response = new Response(id, responseTxt, markup);

        return response;
    }

    public boolean isCorrectAnswer(String answer)
    {
        if(buffAnswer.equals(answer))
            return true;
        else
            return false;
    }
}
