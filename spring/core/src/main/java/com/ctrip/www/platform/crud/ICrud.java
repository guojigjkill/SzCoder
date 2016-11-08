package com.ctrip.www.platform.crud;

import com.ctrip.www.platform.context.IContext;
import com.ctrip.www.platform.entity.IEntity;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface ICrud<T extends IEntity> {
    T get(String id);
    boolean update(T entity);
    String create(T entity);
    boolean delete(String id);
}
