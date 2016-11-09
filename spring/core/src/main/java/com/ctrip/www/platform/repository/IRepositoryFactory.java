package com.ctrip.www.platform.repository;

import com.ctrip.www.platform.entity.IEntity;

import java.lang.reflect.Type;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IRepositoryFactory {
    <T extends IEntity> IRepository<T> GetRepository(String dbName, Class<T> clazz);
}
