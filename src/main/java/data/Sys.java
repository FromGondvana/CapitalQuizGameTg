package data;

public class Sys {
    private static final String BOT_NAME = {BOT_NAME}
    private static final String BOT_USERNAME = {BOT_USERNAME};
    private static final String BOT_TOKEN = {BOT_TOKEN};

    private static final int SIZE_FAST_GAME = 7;
    private static final int SIZE_MINI_GAME = 12;
    private static final int SIZE_MIDDLE_GAME = 20;
    private static final int SIZE_LARGE_GAME = 40;
    private static final int COUNT_SIZE_QUESTION_LIST = 193;


    public static String name()
    {
        return BOT_NAME;
    }

    public static String uname()
    {
        return BOT_USERNAME;
    }

    public static String token()
    {
        return BOT_TOKEN;
    }

    public static int sizeFast()
    {
        return SIZE_FAST_GAME;
    }

    public static int sizeMini()
    {
        return SIZE_MINI_GAME;
    }

    public static int sizeMidlle()
    {
        return SIZE_MIDDLE_GAME;
    }

    public static int sizeLarge()
    {
        return SIZE_LARGE_GAME;
    }

    public static int sizeList()
    {
        return COUNT_SIZE_QUESTION_LIST;
    }
}
