package com.ctrip.www.service.context;

import com.ctrip.www.platform.context.IContext;
import com.ctrip.www.platform.context.IContextRepository;

/**
 * Created by wang.na on 2016/11/7.
 */
public class ServiceContextRepository implements IContextRepository {
    @Override
    public IContext GetCurrent() {
        return null;
    }
}
