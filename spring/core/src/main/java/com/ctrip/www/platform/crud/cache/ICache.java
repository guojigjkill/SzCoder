package com.ctrip.www.platform.crud.cache;

import com.ctrip.www.platform.context.IContext;
import com.ctrip.www.platform.crud.ICrud;
import com.ctrip.www.platform.entity.IEntity;

/**
 * Created by wang.na on 2016/11/7.
 */

public interface ICache<T extends IEntity> extends ICrud {
    void init();
    void destroy();
}
