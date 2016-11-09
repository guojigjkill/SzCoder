package com.ctrip.www.db.impl.mongodb;

import com.ctrip.www.platform.crud.db.IDb;
import com.ctrip.www.platform.crud.db.IDbCollection;
import com.ctrip.www.platform.entity.IEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by wang.na on 2016/11/7.
 */
public class MongoCollection<T extends IEntity> implements IDbCollection<T> {
    private MongoDb db;
    private String collectionName;
    Class<T> clazz;

    public MongoCollection(MongoDb db, Class<T> clazz){
        this.db = db;
        this.collectionName = clazz.getSimpleName();
        this.clazz = clazz;
    }

    @Override
    public T get(String id) {
        return db.getTemplate().findById(id, clazz);
    }

    @Override
    public boolean update(T entity) {
        db.getTemplate().save(entity);
        return true;
    }

    @Override
    public String create(T entity) {
        db.getTemplate().insert(entity);
        return entity.getId();
    }

    @Override
    public boolean delete(String id) {
        db.getTemplate().remove(new Query(Criteria.where("id").is(id)), clazz);
        return true;
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
