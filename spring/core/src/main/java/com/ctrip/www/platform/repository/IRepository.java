package com.ctrip.www.platform.repository;

import com.ctrip.www.platform.crud.ICrud;
import com.ctrip.www.platform.entity.IEntity;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IRepository<T extends IEntity> extends ICrud<T> {
}
