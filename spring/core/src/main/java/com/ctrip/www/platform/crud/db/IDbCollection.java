package com.ctrip.www.platform.crud.db;

import com.ctrip.www.platform.crud.ICrud;
import com.ctrip.www.platform.entity.IEntity;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IDbCollection<T extends IEntity> extends ICrud<T> {

}
