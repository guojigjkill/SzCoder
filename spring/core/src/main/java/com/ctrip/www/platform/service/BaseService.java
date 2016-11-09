package com.ctrip.www.platform.service;

import com.ctrip.www.platform.context.IContext;
import com.ctrip.www.platform.context.IContextRepository;
import com.ctrip.www.platform.entity.IEntity;
import com.ctrip.www.platform.repository.IRepository;
import com.ctrip.www.platform.repository.IRepositoryFactory;
import com.sun.glass.ui.SystemClipboard;

import java.util.Date;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseService<T extends IEntity> implements IService {
    private IContextRepository contextRepository;
    private IRepository repository;

    public BaseService(IRepositoryFactory repositoryFactory, IContextRepository contextRepository, String dbName, Class<T> clazz){
        this.contextRepository = contextRepository;
        this.repository = repositoryFactory.GetRepository(dbName, clazz);
    }

    public IRepository GetRepository(){
        return this.repository;
    }

    @Override
    public IContext getContext() {
        try{
            return contextRepository.getCurrent();
        }
        catch (Exception ex){
            return null;
        }
    }

    public T get(String id){
        System.out.print("get: " + id);
        return (T)this.repository.get(id);
    }

    public String create(T entity){
        System.out.print("create: " + entity.getId());
        entity.setCreated(new Date());
        return this.repository.create(entity);
    }

    public boolean update(T entity){
        System.out.print("update: " + entity.getId());
        entity.setLastUpdated(new Date());
        entity.setLastUpdatedBy(getContext().getId());
        return this.repository.update(entity);
    }

    public boolean delete(String id){
        System.out.print("delete: " + id);
        return this.repository.delete(id);
    }
}
