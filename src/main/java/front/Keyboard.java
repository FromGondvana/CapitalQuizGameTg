package front;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {
    private ReplyKeyboardMarkup markup;
    private ArrayList<KeyboardRow> rows;
    private KeyboardRow firstRow;
    private KeyboardRow secondRow;
    private KeyboardRow thirdRow;
    private ArrayList<String> strKeys;

    public Keyboard()
    {
        markup = new ReplyKeyboardMarkup();
        rows = new ArrayList<>();
        strKeys = new ArrayList<>();
        firstRow = new KeyboardRow();
        secondRow = new KeyboardRow();
        thirdRow = new KeyboardRow();
    }

    public void initMainKeyboard()
    {
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();
        rows.clear();

        firstRow.add(new KeyboardButton("Играть"));
        firstRow.add(new KeyboardButton("Статистика"));
        firstRow.add(new KeyboardButton("Инфо"));

        rows.add(firstRow);
    }

    public void initGameKeyboard(List<String> newKeyTextList){
        strKeys.addAll(newKeyTextList);
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();
        rows.clear();

        int rand = (int)(Math.random() * 3);
        String key = strKeys.get(rand);
        firstRow.add(new KeyboardButton(key));
        strKeys.remove(rand);


        rand = (int)(Math.random() * 2);
        firstRow.add(new KeyboardButton(strKeys.get(rand)));
        strKeys.remove(rand);

        rand = (int)(Math.random() * 1);
        secondRow.add(new KeyboardButton(strKeys.get(rand)));
        strKeys.remove(rand);

        secondRow.add(new KeyboardButton(strKeys.get(rand)));
        strKeys.remove(rand);

        thirdRow.add(new KeyboardButton("Стоп"));

        rows.add(firstRow);
        rows.add(secondRow);
        rows.add(thirdRow);
    }

    public void updateKeyboard()
    {
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);

        markup.setKeyboard(rows);
    }

    public ReplyKeyboardMarkup getMarkup() {
        return markup;
    }
}
