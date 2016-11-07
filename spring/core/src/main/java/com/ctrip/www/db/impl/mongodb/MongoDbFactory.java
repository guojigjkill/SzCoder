package com.ctrip.www.db.impl.mongodb;

import com.ctrip.www.platform.crud.db.IDb;
import com.ctrip.www.platform.crud.db.IDbFactory;
import com.ctrip.www.platform.crud.db.IDbSetting;
import com.ctrip.www.platform.crud.db.IDbSettings;
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
public class MongoDbFactory implements IDbFactory {
    private Map<String, IDbSetting> dbSettings;
    private Map<String, IDb> dbs;

    @Autowired
    public MongoDbFactory(IDbSettings dbSettings){
        this.dbs = new HashMap<String, IDb>();

        this.dbSettings = dbSettings.getSettings();
        for ( Map.Entry<String, IDbSetting> entry : this.dbSettings.entrySet()) {
            IDb db = new MongoDb(entry.getValue());
            this.dbs.put(entry.getKey(), db);
        }
    }

    @Override
    public IDb getDb(String name) {
        return this.dbs.get(name);
    }
}
