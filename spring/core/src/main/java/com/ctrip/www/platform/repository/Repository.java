package com.ctrip.www.platform.repository;

import com.ctrip.www.platform.crud.cache.ICache;
import com.ctrip.www.platform.crud.cache.ICacheFactory;
import com.ctrip.www.platform.crud.db.IDbCollection;
import com.ctrip.www.platform.crud.db.IDb;
import com.ctrip.www.platform.crud.db.IDbFactory;
import com.ctrip.www.platform.entity.IEntity;

/**
 * Created by wang.na on 2016/11/7.
 */
public class Repository<T extends IEntity> implements IRepository<T> {
    IDb db;
    ICache<T> cache;
    IDbCollection<T> dbCollection;

    public Repository(ICache cache, IDb db){
        this.cache = cache;
        this.db = db;
        this.dbCollection = this.db.getCollection(getClass().getGenericSuperclass().getTypeName());
    }

    //ToDo: 区分不存在和空值的情况
    public T get(String id){
        T entity = (T)cache.get(id);

        if(entity == null){
            //从数据库读取，并更新到缓存

            return (T)dbCollection.get(id);
        }
        else{
            return entity;
        }
    }

    public boolean update(T entity){
        cache.update(entity);
        dbCollection.update(entity);

        return true;
    }

    public boolean delete(String id){
        cache.delete(id);
        dbCollection.delete(id);

        return true;
    }

    public boolean create(T entity){
        cache.create(entity);
        dbCollection.create(entity);

        return true;
    }
}
