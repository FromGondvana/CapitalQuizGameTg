package keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class GameKeyboard {
    //private ReplyKeyboardRemove replyKeyboardRemove;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ArrayList<KeyboardRow> keyboardRows;
    private KeyboardRow firstKeyRow;
    private KeyboardRow secondKeyRow;
    private ArrayList<String> keyTextList;
    //private KeyboardRow thirdKeyRow;

    public GameKeyboard()
    {
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        //replyKeyboardRemove = new ReplyKeyboardRemove();
        keyboardRows = new ArrayList<>();
        keyTextList = new ArrayList<>();
        firstKeyRow = new KeyboardRow();
        secondKeyRow = new KeyboardRow();
        //thirdKeyRow = new KeyboardRow();
    }

    public void initKeyboardRows(List<String> newKeyTextList){
        keyTextList.addAll(newKeyTextList);
        firstKeyRow.clear();
        secondKeyRow.clear();
        keyboardRows.clear();

        int rand = (int)(Math.random() * 3);
        String key = keyTextList.get(rand);
        firstKeyRow.add(new KeyboardButton(key));
        keyTextList.remove(rand);


        rand = (int)(Math.random() * 2);
        firstKeyRow.add(new KeyboardButton(keyTextList.get(rand)));
        keyTextList.remove(rand);

        rand = (int)(Math.random() * 1);
        secondKeyRow.add(new KeyboardButton(keyTextList.get(rand)));
        keyTextList.remove(rand);

        secondKeyRow.add(new KeyboardButton(keyTextList.get(rand)));
        keyTextList.remove(rand);

        keyboardRows.add(firstKeyRow);
        keyboardRows.add(secondKeyRow);
    }

    public void updateKeyboard()
    {
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public ReplyKeyboardMarkup getKeyboardMarkup() {
        return replyKeyboardMarkup;
    }
}
