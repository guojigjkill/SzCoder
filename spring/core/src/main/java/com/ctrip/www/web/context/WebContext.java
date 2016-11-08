package com.ctrip.www.web.context;

import com.ctrip.www.platform.context.IContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by wang.na on 2016/11/8.
 */
public class WebContext implements IContext {
    private String userId;
    private String remoteIp;

    public WebContext(String userId, String remoteIp){
        this.userId = userId;
        this.remoteIp = remoteIp;
    }

    @Override
    public String getId() {
        return userId;
    }

    public String getRemoteIp(){
        return remoteIp;
    }
}
