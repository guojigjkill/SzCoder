package com.ctrip.www.platform.service;

import com.ctrip.www.platform.context.IContext;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IService {
    IContext getContext() throws Exception;
}
