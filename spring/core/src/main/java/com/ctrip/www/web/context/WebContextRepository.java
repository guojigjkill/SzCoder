package com.ctrip.www.web.context;

import com.ctrip.www.platform.context.IContext;
import com.ctrip.www.platform.context.IContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang.na on 2016/11/8.
 */
@Repository
public class WebContextRepository implements IContextRepository{
    private Map<String, IContext> contexts;

    public WebContextRepository(){
        this.contexts = new HashMap<String, IContext>();
    }

    @Override
    public void add(String key, IContext context){
        contexts.put(key, context);
    }

    @Override
    public void remove(String key){
        if(contexts.containsKey(key)){
            contexts.remove(key);
        }
    }

    @Override
    public void clear(){
        contexts.clear();
    }

    @Override
    public IContext getCurrent() throws Exception {
        String key = String.valueOf(Thread.currentThread().getId());
        System.out.println(key);
        if(contexts.containsKey(key)){
            return contexts.get(key);
        }
        else {
            throw new Exception("No web context.");
        }
    }
}
