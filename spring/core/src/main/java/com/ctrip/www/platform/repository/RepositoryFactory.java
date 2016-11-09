package com.ctrip.www.platform.repository;

import com.ctrip.www.platform.crud.cache.ICache;
import com.ctrip.www.platform.crud.cache.ICacheFactory;
import com.ctrip.www.platform.crud.db.IDbCollection;
import com.ctrip.www.platform.crud.db.IDbFactory;
import com.ctrip.www.platform.entity.IEntity;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang.na on 2016/11/7.
 */
@Component
public class RepositoryFactory implements IRepositoryFactory {
    private IDbFactory dbFactory;
    private ICacheFactory cacheFactory;
    private Map<String, Object> repositories;

    public RepositoryFactory(IDbFactory dbFactory, ICacheFactory cacheFactory){
        this.dbFactory = dbFactory;
        this.cacheFactory = cacheFactory;
        this.repositories = new HashMap<String, Object>();
    }

    @Override
    public <T extends IEntity> IRepository<T> GetRepository(String dbName, Class<T> clazz) {
        String key = dbName + "." + clazz.getSimpleName();
        if(!repositories.containsKey(key)){
            IDbCollection<T> dbCollection = dbFactory.getDb(dbName).getCollection(clazz);
            ICache cache = cacheFactory.getCache(dbName, clazz.getSimpleName());

            repositories.put(key, new Repository<T>(cache, dbCollection));
        }

        return (IRepository<T>) repositories.get((key));
    }
}
