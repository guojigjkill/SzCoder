package com.ctrip.www.db.impl.mongodb;

import com.ctrip.www.platform.crud.db.IDbCollection;
import com.ctrip.www.platform.crud.db.IDb;
import com.ctrip.www.platform.crud.db.IDbSetting;
import com.ctrip.www.platform.entity.IEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * Created by wang.na on 2016/11/7.
 */
@Component
public class MongoDb implements IDb {
    IDbSetting dbSetting;

    public MongoDb(IDbSetting dbSetting){
        this.dbSetting = dbSetting;
    }

    @Override
    public IDbSetting GetDbSetting() {
        return this.dbSetting;
    }

    @Override
    public <T extends IEntity> IDbCollection<T> getCollection(String name) {
        return null;
    }
}
