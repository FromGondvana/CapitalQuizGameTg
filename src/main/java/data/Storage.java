package data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Question> questions;
    private Path bufferedFilePath = Paths.get("src/main/resources/data/questions.txt");

    public Storage()
    {
        questions = new ArrayList<>();
    }

    public void initLists()
    {
        startFillingStrorage(bufferedFilePath);
    }

    void startFillingStrorage(Path path) {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.stream().forEach(l -> {
                String q = l.substring(0, l.indexOf("$")).trim();
                String ans = l.substring(l.indexOf("$") + 1).trim();
                questions.add(new Question(q, ans));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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
