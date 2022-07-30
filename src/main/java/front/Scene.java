package front;

import back.PackValue;
import data.Storage;
import data.Sys;
import keyboards.GameKeyboard;
import keyboards.MainKeyboard;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private Logger log;
    private GameKeyboard gameKeyboard;
    //private MainKeyboard mainKeyboard;
    private Storage gameData;
    private boolean isEnabledGame;
    private String buffAnswer;
    private List<String> buffChoises;
    private int countQuestion;

    public Scene()
    {
        log = Logger.getLogger(Scene.class);
        log.info("Starting constructor");
        gameKeyboard = new GameKeyboard();
        //mainKeyboard = new MainKeyboard();
        //mainKeyboard.initMainKeyboard();
        isEnabledGame = false;
        gameData = new Storage();
        gameData.initLists();
        countQuestion = gameData.getSizeQList();
        buffChoises = new ArrayList<>();
    }

    public PackValue initFirstGameMess(String id)
    {
        log.info("Starting metod with id ".concat(id));
        isEnabledGame = true;
        PackValue response;

        response = initMessageQuestion(id, 1);

        return response;
    }

    public PackValue initMessageQuestion(String id, int roundNum)
    {
        log.info("Starting metod with id ".concat(id));
        PackValue response;

        List<String> choiseStrList = new ArrayList<>();
        List<Integer> choiseIndList = new ArrayList<>();
        String correctAns;
        String question = Integer.toString(roundNum).concat(") ");
        int size = gameData.getSizeQList() - 1;
        int choiseBuff = (int) (Math.random() * size);
        choiseIndList.add(choiseBuff);
        choiseStrList.add(gameData.getQuestion(choiseBuff).getAnswer());
        correctAns = gameData.getQuestion(choiseBuff).getAnswer();
        question = question.concat(gameData.getQuestion(choiseBuff).getQuestion());

        buffChoises.clear();
        buffChoises.addAll(choiseStrList);
        buffAnswer = correctAns;

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

        response = new PackValue(id, question, correctAns, keyboard);

        return response;
    }

    public PackValue initFinalMessage(String id, int result, ReplyKeyboardMarkup markup)
    {
        log.info("Starting metod with id ".concat(id));
        PackValue response;
        String responseTxt;

        buffChoises.clear();

        responseTxt = "Вопросы закончились\n"
                .concat(Integer.toString(result))
                .concat(" - количество правильных ответов\n")
                //.concat(Integer.toString(Sys.size()))
                //.concat("\n")
                .concat(Double.toString(((double) result) / ((double) Sys.size()) * 100))
                .concat("% - ваш результат. Попробуем еще?");
        response = new PackValue(id, responseTxt, markup);

        return response;
    }

    public boolean isHasInBuffChoises(String choise)
    {
        if(buffChoises.contains(choise))
            return true;
        else
            return false;
    }

    public String getBuffAnswer() {
        return buffAnswer;
    }

    public boolean isCorrectAnswer(String answer)
    {
        if(buffAnswer.equals(answer))
            return true;
        else
            return false;
    }
    public String initRespMess(String answer)
    {
        if(buffAnswer.equals(answer)) {
            return "Это правильный ответ\n";
        }
        else
            return "Это неправильный ответ\n";
    }

    public int getCountQuestion() {
        return countQuestion;
    }
}
