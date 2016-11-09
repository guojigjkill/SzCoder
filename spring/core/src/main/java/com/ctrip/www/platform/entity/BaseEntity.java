package com.ctrip.www.platform.entity;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseEntity implements IEntity {
    @Id
    public String id;

    public Date created;

    public Date lastUpdated;

    public String createdBy;

    public String lastUpdatedBy;

    @Override
    public String getId(){
        return id;
    }

    @Override
    public Date getCreated(){
        return created;
    }

    @Override
    public void setCreated(Date created){
        this.created = created;
    }

    @Override
    public Date getLastUpdated(){
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(Date lastUpdated){
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String getCreatedBy(){
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    @Override
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    @Override
    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
