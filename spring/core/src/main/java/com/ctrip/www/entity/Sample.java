package com.ctrip.www.entity;

import com.ctrip.www.platform.entity.BaseEntity;
import com.ctrip.www.platform.entity.IEntity;

/**
 * Created by wang.na on 2016/11/7.
 */
public class Sample extends BaseEntity {
    private String id;

    public Sample(String id){
        setId(id);
    }
}
