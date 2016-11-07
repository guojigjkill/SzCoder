package com.ctrip.www.web.setting;

import com.ctrip.www.db.impl.mongodb.MongoDbSetting;
import com.ctrip.www.platform.crud.db.IDbSetting;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang.na on 2016/11/7.
 */
public class DbSettings {
    private static DbSettings instance = new DbSettings();

    private Map<String, IDbSetting> settings;

    public static DbSettings getInstance() {
        return instance;
    }

    private DbSettings() {
        this.settings = new HashMap<String, IDbSetting>();
        settings.put("Comment", new MongoDbSetting("Comment"));
    }

    public IDbSetting getDbSetting(String name){
        return settings.get(name);
    }
}
