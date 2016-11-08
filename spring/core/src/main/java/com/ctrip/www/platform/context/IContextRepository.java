package com.ctrip.www.platform.context;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IContextRepository {
    IContext getCurrent() throws Exception;
    void add(String key, IContext context);
    void remove(String key);
    void clear();
}
