package data;

public class Sys {
    private static final String BOT_NAME = "Capital city quiz";
    private static final String BOT_USERNAME = "Quiz_capital_bot";
    private static final String BOT_TOKEN = "5454334849:AAGjhO67eC5iNVty6X6uy1xRS0VwbjZTS9g";

    private static final int COUNT_SIZE_GAME_QLIST = 4;


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

    public static int size()
    {
        return COUNT_SIZE_GAME_QLIST;
    }
}
