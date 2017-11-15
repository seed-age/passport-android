package cc.seedland.inf.passport.util;

import com.google.gson.Gson;

/**
 * Created by Ryan.Xu on 2017/5/10.
 */

public class GsonHolder {

    private static final Gson INSTANCE = new Gson();

    private GsonHolder() {

    }

    public static Gson getInstance() {
        return INSTANCE;
    }
}
