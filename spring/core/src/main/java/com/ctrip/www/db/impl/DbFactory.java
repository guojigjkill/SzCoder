package com.ctrip.www.db.impl;

import com.ctrip.www.db.impl.mongodb.MongoDb;
import com.ctrip.www.platform.crud.db.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang.na on 2016/11/7.
 */
@Component
public class DbFactory implements IDbFactory {
    private Map<String, IDbSetting> dbSettings;
    private Map<String, IDb> dbs;

    private final MongoDbFactory mongoDbFactory;

    @Autowired
    public DbFactory(IDbSettings dbSettings, MongoDbFactory mongoDbFactory){
        this.mongoDbFactory = mongoDbFactory;
        this.dbs = new HashMap<>();

        this.dbSettings = dbSettings.getSettings();
        for ( Map.Entry<String, IDbSetting> entry : this.dbSettings.entrySet()) {
            this.dbs.put(entry.getKey(), createDb(entry.getValue()));
        }
    }

    public IDb createDb(IDbSetting dbSetting){
        switch (dbSetting.getType()){
            case MongoDb: return new MongoDb(dbSetting, new MongoTemplate(mongoDbFactory));
            default:break;
        }

        return null;
    }

    @Override
    public IDb getDb(String name) {
        return this.dbs.get(name);
    }
}
