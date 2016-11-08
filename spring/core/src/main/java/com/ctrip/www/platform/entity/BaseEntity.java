package com.ctrip.www.platform.entity;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseEntity implements IEntity {
    private String id;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
