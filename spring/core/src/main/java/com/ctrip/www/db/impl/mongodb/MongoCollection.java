package com.ctrip.www.db.impl.mongodb;

import com.ctrip.www.platform.crud.db.IDb;
import com.ctrip.www.platform.crud.db.IDbCollection;
import com.ctrip.www.platform.entity.IEntity;
import org.springframework.stereotype.Component;

/**
 * Created by wang.na on 2016/11/7.
 */
public class MongoCollection<T extends IEntity> implements IDbCollection<T> {
    private IDb db;
    private String collectionName;

    public MongoCollection(IDb db, String collectionName){
        this.db = db;
        this.collectionName = collectionName;
    }

    @Override
    public T get(String id) {
        return null;
    }

    @Override
    public boolean update(T entity) {
        return false;
    }

    @Override
    public boolean create(T entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public IDb getDb() {
        return db;
    }

    @Override
    public String getCollectionName() {
        return collectionName;
    }
}
