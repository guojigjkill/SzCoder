package com.ctrip.www.platform.crud.db;

import com.ctrip.www.platform.entity.IEntity;

import java.lang.reflect.Type;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IDb {
    IDbSetting GetDbSetting();
    <T extends IEntity> IDbCollection<T> getCollection(String name);
}
