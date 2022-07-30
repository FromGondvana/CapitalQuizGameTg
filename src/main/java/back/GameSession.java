package back;

import data.Sys;

import java.util.ArrayList;
import java.util.Objects;

public class GameSession {
    private int roundNumber;
    private int bound;
    private int scoreCorrect;
    private String id;

    private ArrayList<Integer> usedIndexList;

    public GameSession(String id, int bound) {
        this.id = id;
        this.bound = bound;
        this.roundNumber = 1;
        this.scoreCorrect = 0;

        usedIndexList = new ArrayList<>();

        while (usedIndexList.size() < 4)
        {
            int rand = (int) (Math.random() * bound);
            if(!usedIndexList.contains(rand))
                usedIndexList.add(rand);
        }
    }

    public int getNextIndexQuestion()
    {
        return usedIndexList.get(roundNumber - 1);
    }

    public void nextStep(boolean answer)
    {
        roundNumber = roundNumber + 1;
        if(answer) {
            scoreCorrect = scoreCorrect + 1;
        }
    }

    public boolean isEqualsId(String id)
    {
        if(id.equals(this.id))
            return true;
        else
            return false;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSession that = (GameSession) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isFinal()
    {
        if(bound <= roundNumber)
            return true;
        else
            return false;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getResult()
    {
        return scoreCorrect;
    }
}
