package com.ctrip.www.db.impl;

import com.ctrip.www.db.impl.mongodb.MongoDb;
import com.ctrip.www.platform.crud.db.*;
import com.ctrip.www.platform.entity.IEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang.na on 2016/11/7.
 */
@Component
public class DbFactory implements IDbFactory {
    private Map<String, IDbSetting> dbSettings;
    private Map<String, IDb> dbs;

    @Autowired
    public DbFactory(IDbSettings dbSettings){
        this.dbs = new HashMap<String, IDb>();

        this.dbSettings = dbSettings.getSettings();
        for ( Map.Entry<String, IDbSetting> entry : this.dbSettings.entrySet()) {
            this.dbs.put(entry.getKey(), createDb(entry.getValue()));
        }
    }

    public IDb createDb(IDbSetting dbSetting){
        switch (dbSetting.getType()){
            case MongoDb: return new MongoDb(dbSetting);
            default:break;
        }

        return null;
    }

    @Override
    public IDb getDb(String name) {
        return this.dbs.get(name);
    }
}
