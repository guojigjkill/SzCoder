package com.ctrip.www.db.impl.mongodb;

import com.ctrip.www.platform.crud.db.IDbSetting;

/**
 * Created by wang.na on 2016/11/7.
 */
public class MongoDbSetting implements IDbSetting{
    private String name;

    public MongoDbSetting(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
