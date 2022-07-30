package data;

import back.GameSession;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private List<String> chatIdList = new ArrayList<>();

    public void addId(String id) {
        if (!chatIdList.contains(id)) {
            chatIdList.add(id);
        }
    }

    public boolean isHasId(String id)
    {
        if (chatIdList.contains(id))
            return true;
        else
            return false;
    }
}
