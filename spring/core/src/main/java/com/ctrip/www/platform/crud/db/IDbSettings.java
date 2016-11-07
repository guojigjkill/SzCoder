package com.ctrip.www.platform.crud.db;

import java.util.Map;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IDbSettings {
    Map<String, IDbSetting> getSettings();
}
