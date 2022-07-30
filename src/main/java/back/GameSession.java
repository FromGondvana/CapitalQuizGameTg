package back;

import java.util.Objects;

public class GameSession {
    private int roundNumber;
    private int bound;
    private int scoreCorrect;
    private String id;

    public GameSession(String id, int bound) {
        this.id = id;
        this.bound = bound;
        this.roundNumber = 1;
        this.scoreCorrect = 0;
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

    public int getCurrent() {
        return roundNumber;
    }
    public boolean isFinal()
    {
        if(bound <= roundNumber)
            return true;
        else
            return false;
    }

    public boolean isPreFinal()
    {
        if(bound == (roundNumber - 1))
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

    public int getProcent()
    {
        double procent = ((double) scoreCorrect) / ((double) roundNumber) * 100;
        int res = (int) procent;
        return res;
    }

}
