package com.ctrip.www.cache.local;

import com.ctrip.www.platform.crud.cache.ICache;
import com.ctrip.www.platform.entity.IEntity;

/**
 * Created by wang.na on 2016/11/9.
 */
public class LocalCache<T extends IEntity> implements ICache<T> {

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
        return null;
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
