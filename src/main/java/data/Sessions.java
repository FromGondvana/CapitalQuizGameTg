package data;

import back.GameSession;

import java.util.ArrayList;
import java.util.List;

public class Sessions {
    private List<GameSession> gameSessions = new ArrayList<>();

    public void add(GameSession playGame) {
        if (!gameSessions.contains(playGame)) {
            gameSessions.add(playGame);
        }
    }
    public void delete(String id) {

        int index = 0;
        for(GameSession test : gameSessions)
        {
            if(test.isEqualsId(id))
                break;
            else
                index = index + 1;
        }

        gameSessions.remove(index);

    }

    public GameSession get(String id) {
        for(GameSession test : gameSessions)
        {
            if(test.isEqualsId(id))
                return test;
        }
        return null;
    }

    public boolean isHasWithId(String id)
    {
        for(GameSession test : gameSessions)
        {
            if(test.isEqualsId(id))
                return true;
        }
        return false;
    }
}
