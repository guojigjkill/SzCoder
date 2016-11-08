package com.ctrip.www.web.setting;

import com.ctrip.www.db.impl.mongodb.MongoDbSetting;
import com.ctrip.www.platform.crud.db.IDbSetting;
import com.ctrip.www.platform.crud.db.IDbSettings;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang.na on 2016/11/7.
 */
@Component
public class DbSettings implements IDbSettings {
    private Map<String, IDbSetting> settings;

    public DbSettings() {
        this.settings = new HashMap<String, IDbSetting>();
        settings.put("Comment", new MongoDbSetting("Comment"));
        settings.put("dbName", new MongoDbSetting("dbName"));
    }

    public IDbSetting getDbSetting(String name){
        return settings.get(name);
    }

    @Override
    public Map<String, IDbSetting> getSettings() {
        return this.settings;
    }
}
