package data;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Question> questions;
    //private HashMap<Integer, String> capitals;

    public Storage()
    {
        questions = new ArrayList<>();
    }

    public void initLists()
    {
        questions.add(new Question("Столица Аргентины", "Буэнос Айрес"));
        questions.add(new Question("Столица КНДР", "Пхеньян"));
        questions.add(new Question("Столица Бангладеша", "Дакка"));
        questions.add(new Question("Столица Ирана", "Тегеран"));
        questions.add(new Question("Столица Румынии", "Бухарест"));
        questions.add(new Question("Столица Камбоджи", "Пномпень"));

        /*int i = 0;
        for(Question question : questions)
        {
            capitals.put(i, question.getAnswer());
            i = i++;
        }*/
    }

    public String getQuestionStr(int num)
    {
        String response = questions.get(num - 1).getQuestion();

        return response;
    }
    public String getAnswer(int num)
    {
        String response = questions.get(num - 1).getAnswer();

        return response;
    }
    public Question getRandQuestion()
    {
        int size = questions.size();
        return questions.get((int) (Math.random() * size));
    }

    public Question getQuestion(int num) {
        return questions.get(num);
    }

    public int getSizeQList()
    {
        return questions.size();
    }
}
