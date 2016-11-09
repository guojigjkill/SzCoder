package com.ctrip.www.db.impl.mongodb;

import com.ctrip.www.platform.context.IContext;
import com.ctrip.www.platform.crud.db.IDbCollection;
import com.ctrip.www.platform.crud.db.IDb;
import com.ctrip.www.platform.crud.db.IDbSetting;
import com.ctrip.www.platform.entity.IEntity;
import com.mongodb.DB;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
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
    private MongoTemplate template;

    public MongoDb(IDbSetting dbSetting, MongoTemplate template){
        this.dbSetting = dbSetting;
        this.dbCollections = new HashMap<>();
        this.template = template;
    }

    @Override
    public IDbSetting getDbSetting() {
        return dbSetting;
    }

    public MongoTemplate getTemplate(){
        return template;
    }

    @Override
    public <T extends IEntity> IDbCollection<T> getCollection(Class<T> clazz) {
        String key = clazz.getName();
        if(dbCollections.containsKey(key)){
            return dbCollections.get(key);
        }
        else{
            dbCollections.put(key, new MongoCollection(this, clazz));
        }

        return dbCollections.get(key);
    }
}
