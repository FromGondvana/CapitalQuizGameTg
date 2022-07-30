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
    private List<String> chatIdList;
    private List<Session> sessions;

    private Path bufferedFilePath = Paths.get("src/main/resources/data/questions.txt");

    public Storage()
    {
        questions = new ArrayList<>();
        chatIdList = new ArrayList<>();
        sessions = new ArrayList<>();
    }

    public void initLists()
    {
        startFillingStrorage(bufferedFilePath);
    }

    void startFillingStrorage(Path path) {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for(String line : lines)
            {
                int index = line.lastIndexOf("$");
                String continent = line.substring(index + 1).trim();
                line = line.substring(0, index);

                index = line.lastIndexOf("$");
                String city = line.substring(index + 1).trim();

                line = line.substring(0, index);
                String country = line;
                questions.add(new Question(country, city, continent));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Question getQuestion(int num) {
        return questions.get(num);
    }

    public void addId(String id) {
        if (!chatIdList.contains(id)) {
            chatIdList.add(id);
        }
    }

    public boolean isHasId(String id)
    {
        if (chatIdList.contains(id))
            return true;
        else
            return false;
    }


    public void createSession(Session playGame)
    {
        if (!sessions.contains(playGame)) {
            sessions.add(playGame);
        }
    }
    public void deleteSession(String id) {

        int index = 0;
        for(Session test : sessions)
        {
            if(test.isEqualsId(id))
                break;
            else
                index = index + 1;
        }

        sessions.remove(index);

    }

    public Session getSession(String id) {
        for(Session test : sessions)
        {
            if(test.isEqualsId(id))
                return test;
        }
        return null;
    }

    public boolean isHasSessionWithId(String id)
    {
        for(Session test : sessions)
        {
            if(test.isEqualsId(id))
                return true;
        }
        return false;
    }
}
