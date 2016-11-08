package com.ctrip.www.cache.redis;

import com.ctrip.www.platform.crud.cache.ICache;
import com.ctrip.www.platform.crud.cache.ICacheFactory;
import com.ctrip.www.platform.entity.IEntity;
import org.springframework.stereotype.Component;

/**
 * Created by wang.na on 2016/11/8.
 */
@Component
public class RedisCacheFactory implements ICacheFactory {
    @Override
    public <T extends IEntity> ICache getCache(String dbName, String collectionName) {
        return null;
    }
}
