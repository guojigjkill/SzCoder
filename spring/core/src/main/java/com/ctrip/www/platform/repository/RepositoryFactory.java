package com.ctrip.www.platform.repository;

import com.ctrip.www.platform.crud.cache.ICache;
import com.ctrip.www.platform.crud.cache.ICacheFactory;
import com.ctrip.www.platform.crud.db.IDb;
import com.ctrip.www.platform.crud.db.IDbFactory;
import com.ctrip.www.platform.entity.IEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * Created by wang.na on 2016/11/7.
 */
@Component
public class RepositoryFactory implements IRepositoryFactory {
    private IDbFactory dbFactory;
    private ICacheFactory cacheFactory;

    public RepositoryFactory(IDbFactory dbFactory, ICacheFactory cacheFactory){
        this.dbFactory = dbFactory;
        this.cacheFactory = cacheFactory;
    }

    @Override
    public <T extends IEntity> IRepository<T> GetRepository(String dbName, String collectionName) {
        IDb db = dbFactory.getDb(dbName);
        ICache cache = cacheFactory.getCache(dbName, collectionName);

        return new Repository<T>(cache, db);
    }
}
