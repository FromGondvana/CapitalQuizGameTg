package back;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class Response {
    private String id;
    private String txtQ;
    private String correctAnswer;
    private ReplyKeyboard keyboard;

    public Response(String id, String txtQ, String correctAnswer, ReplyKeyboard keyboard) {
        this.id = id;
        this.correctAnswer = correctAnswer;
        this.txtQ = txtQ;
        this.keyboard = keyboard;
    }
    public Response(String id, String txtQ, ReplyKeyboard keyboard) {
        this.id = id;
        this.txtQ = txtQ;
        this.keyboard = keyboard;
    }

    public String getId() {
        return id;
    }

    public String getTxtQ() {
        return txtQ;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ReplyKeyboard getKeyboard() {
        return keyboard;
    }

    public void setTxtQ(String txtQ) {
        this.txtQ = txtQ;
    }

    public void setKeyboard(ReplyKeyboard keyboard) {
        this.keyboard = keyboard;
    }
}
