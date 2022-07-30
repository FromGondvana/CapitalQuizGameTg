package control;

import data.Response;
import data.Storage;
import data.Sys;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    private Keyboard keyboard;
    private Storage gameData;
    private String buffAnswer;
    private List<String> buffChoises;

    public Scene(Storage gameData)
    {
        keyboard = new Keyboard();
        this.gameData = gameData;
        buffChoises = new ArrayList<>();
    }

    public Response initFirstGameMess(String id, int indexQue)
    {
        Response response;
        response = initMessageQuestion(id, 1, indexQue);

        return response;
    }

    public Response initMessageQuestion(String id, int roundNum, int indexQue)
    {
        Response response;

        List<String> choiseStrList = new ArrayList<>();
        List<Integer> choiseIndList = new ArrayList<>();
        String correctAns;
        String question = "Вопрос №".concat(Integer.toString(roundNum)).concat("\n");

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
                choiseBuff = (int) (Math.random() * Sys.sizeList());
            }while (choiseIndList.contains(choiseBuff));

            choiseStrList.add(gameData.getQuestion(choiseBuff).getAnswer());
            choiseIndList.add(choiseBuff);
        }

        keyboard.initGameKeyboard(choiseStrList);
        keyboard.updateKeyboard();
        ReplyKeyboardMarkup markup = keyboard.getMarkup();

        response = new Response(id, question, correctAns, markup);

        return response;
    }

    public Response initFinalMessage(String id, int result, ReplyKeyboardMarkup markup)
    {
        Response response;
        String responseTxt;

        buffChoises.clear();

        responseTxt = "Игра подошла к концу\n"
                .concat(Integer.toString(result))
                .concat(" - количество правильных ответов\n")
                //.concat(Double.toString(((double) result) / ((double) Sys.sizeList()) * 100))
                //.concat("% - ваш результат. Попробуем еще?");
                .concat("Теперь ты помнишь больше. Начнинай скорее новую игру");
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
