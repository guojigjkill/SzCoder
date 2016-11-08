package com.ctrip.www.db.impl.mongodb;

import com.ctrip.www.platform.crud.db.IDb;
import com.ctrip.www.platform.crud.db.IDbCollection;
import com.ctrip.www.platform.entity.IEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by wang.na on 2016/11/7.
 */
public class MongoCollection<T extends IEntity> implements IDbCollection<T> {
    private MongoRepository<T, String> mongoRepository;
    private IDb db;
    private String collectionName;

    public MongoCollection(IDb db, String collectionName, MongoRepository<T, String> mongoRepository){
        this.db = db;
        this.collectionName = collectionName;
        this.mongoRepository = mongoRepository;
    }

    @Override
    public T get(String id) {
        return mongoRepository.findOne(id);
    }

    @Override
    public boolean update(T entity) {
        mongoRepository.save(entity);
        return true;
    }

    @Override
    public String create(T entity) {
        return mongoRepository.insert(entity).getId();
    }

    @Override
    public boolean delete(String id) {
        mongoRepository.delete(id);
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
