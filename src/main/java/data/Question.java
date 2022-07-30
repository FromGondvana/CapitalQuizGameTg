package data;

public class Question {
    private String question;
    private String answer;
    private String continent;

    public Question(String question, String answer, String continent) {
        this.question = question;
        this.answer = answer;
        this.continent = continent;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
