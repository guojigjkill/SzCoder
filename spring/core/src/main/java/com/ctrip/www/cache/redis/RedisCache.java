package com.ctrip.www.cache.redis;

import com.ctrip.www.platform.crud.cache.ICache;
import com.ctrip.www.platform.entity.IEntity;

/**
 * Created by wang.na on 2016/11/8.
 */
public class RedisCache<T extends IEntity> implements ICache<T> {
    @Override
    public IEntity get(String id) {
        return null;
    }

    @Override
    public boolean update(IEntity entity) {
        return false;
    }

    @Override
    public String create(IEntity entity) {
        return "";
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
