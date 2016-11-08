package com.ctrip.www.db.impl.mongodb;

import com.ctrip.www.platform.context.IContext;
import com.ctrip.www.platform.crud.db.IDbCollection;
import com.ctrip.www.platform.crud.db.IDb;
import com.ctrip.www.platform.crud.db.IDbSetting;
import com.ctrip.www.platform.entity.IEntity;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang.na on 2016/11/7.
 */
public class MongoDb implements IDb {
    private IDbSetting dbSetting;
    private Map<String, IDbCollection> dbCollections;

    public MongoDb(IDbSetting dbSetting){
        this.dbSetting = dbSetting;
        this.dbCollections = new HashMap<>();
    }

    @Override
    public IDbSetting getDbSetting() {
        return dbSetting;
    }

    @Override
    public <T extends IEntity> IDbCollection<T> getCollection(String collectionName) {
        if(dbCollections.containsKey(collectionName)){
            return dbCollections.get(collectionName);
        }
        else{
            //ToDo: ctx get instance
            MongoRepository<T, String> mongoRepository = null;
            dbCollections.put(collectionName, new MongoCollection(this, collectionName, mongoRepository));
        }
        return dbCollections.get(collectionName);
    }
}
