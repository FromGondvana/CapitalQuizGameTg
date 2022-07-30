package data;

import java.util.ArrayList;
import java.util.Objects;

public class Session {
    private int roundNumber;
    private int scoreCorrect;
    private String id;

    private ArrayList<Integer> usedIndexList;

    public Session(String id) {
        this.id = id;
        this.roundNumber = 1;
        this.scoreCorrect = 0;

        usedIndexList = new ArrayList<>();

        while (usedIndexList.size() < Sys.sizeFast())
        {
            int rand = (int) (Math.random() * Sys.sizeList());
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
        Session that = (Session) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isFinal()
    {
        if(Sys.sizeFast() <= roundNumber)
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

    public int getScoreCorrect() {
        return scoreCorrect;
    }
}
