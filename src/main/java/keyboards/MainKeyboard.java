package keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class MainKeyboard {
    private ReplyKeyboardRemove replyKeyboardRemove;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ArrayList<KeyboardRow> keyboardRows;
    private KeyboardRow firstKeyRow;

    public MainKeyboard()
    {
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardRemove = new ReplyKeyboardRemove();
        keyboardRows = new ArrayList<>();
        firstKeyRow = new KeyboardRow();

        initMainKeyboard();
    }

    public void initMainKeyboard()
    {
        firstKeyRow.add(new KeyboardButton("Играть"));
        firstKeyRow.add(new KeyboardButton("Статистика"));
        firstKeyRow.add(new KeyboardButton("Инфо"));
    }

    public void updateKeyboard()
    {
        keyboardRows.clear();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        keyboardRows.add(firstKeyRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public ReplyKeyboardMarkup getRepMarkup() {
        return replyKeyboardMarkup;
    }
}
