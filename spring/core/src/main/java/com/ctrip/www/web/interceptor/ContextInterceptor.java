package com.ctrip.www.web.interceptor;

import com.ctrip.www.platform.context.IContextRepository;
import com.ctrip.www.web.context.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wang.na on 2016/11/8.
 */
public class ContextInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    IContextRepository contextRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = String.valueOf(Thread.currentThread().getId());
        contextRepository.add(key, new WebContext(request.getRequestedSessionId(), request.getRemoteAddr()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String key = String.valueOf(Thread.currentThread().getId());
        contextRepository.remove(key);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
