package com.ctrip.www.platform.crud.cache;

import com.ctrip.www.platform.entity.IEntity;

import java.lang.reflect.Type;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface ICacheFactory {
    <T extends IEntity> ICache getCache(String dbName, String collectionName);
}
