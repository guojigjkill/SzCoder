package com.ctrip.www.platform.entity;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseEntity implements IEntity {
    @Id
    public String id;

    public Date Created;

    public Date LastUpdated;

    public String CreatedBy;

    public String LastUpdatedBy;

    public String getId(){
        return id;
    }
}
