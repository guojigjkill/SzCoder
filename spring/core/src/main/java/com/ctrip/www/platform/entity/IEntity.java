package com.ctrip.www.platform.entity;

import java.util.Date;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IEntity {
    String getId();
    Date getCreated();
    void setCreated(Date date);
    Date getLastUpdated();
    void setLastUpdated(Date lastUpdated);
    String getCreatedBy();
    void setCreatedBy(String createdBy);
    String getLastUpdatedBy();
    void setLastUpdatedBy(String lastUpdatedBy);
}
