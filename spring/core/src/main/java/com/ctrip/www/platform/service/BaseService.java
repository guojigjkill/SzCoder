package com.ctrip.www.platform.service;

import com.ctrip.www.platform.context.IContext;
import com.ctrip.www.platform.context.IContextRepository;
import com.ctrip.www.platform.entity.IEntity;
import com.ctrip.www.platform.repository.IRepository;
import com.ctrip.www.platform.repository.IRepositoryFactory;
import com.sun.glass.ui.SystemClipboard;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseService<T extends IEntity> implements IService {
    private IContextRepository contextRepository;
    private IRepository repository;
    private String dbName;
    private String collectionName;

    public BaseService(IRepositoryFactory repositoryFactory, IContextRepository contextRepository, String dbName, String collectionName){
        this.contextRepository = contextRepository;
        this.repository = repositoryFactory.GetRepository(dbName, collectionName);
        this.dbName = dbName;
        this.collectionName = collectionName;
    }

//    public BaseService(IRepositoryFactory repositoryFactory, IContext context, String dbName, String collectionName){
//        this.context = context;
//        this.repository = repositoryFactory.GetRepository(dbName, collectionName);
//        this.dbName = dbName;
//        this.collectionName = collectionName;
//    }

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

    public boolean create(T entity){
        System.out.print("create: " + entity.getId());
        return this.repository.create(entity);
    }

    public boolean update(T entity){
        System.out.print("update: " + entity.getId());
        return this.repository.update(entity);
    }

    public boolean delete(String id){
        System.out.print("delete: " + id);
        return this.repository.delete(id);
    }
}
