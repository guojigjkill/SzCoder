package com.ctrip.www.platform.service;

import com.ctrip.www.platform.context.IContext;
import com.ctrip.www.platform.context.IContextRepository;
import com.ctrip.www.platform.entity.IEntity;
import com.ctrip.www.platform.repository.IRepository;
import com.ctrip.www.platform.repository.IRepositoryFactory;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseService<T extends IEntity> implements IService {
    IRepositoryFactory repositoryFactory;
    IContextRepository contextRepository;
    String dbName;
    String collectionName;
    IRepository repository;

    public BaseService(IRepositoryFactory repositoryFactory, IContextRepository contextRepository, String dbName, String collectionName){
        this.repositoryFactory = repositoryFactory;
        this.contextRepository = contextRepository;
        this.dbName = dbName;
        this.collectionName = collectionName;
        this.repository = repositoryFactory.GetRepository(dbName, collectionName);
    }

    public IRepository GetRepository(){
        return this.repository;
    }

    public IContext getContext(){
        return this.contextRepository.GetCurrent();
    }

    public T get(String id){
        return (T)this.repository.get(id);
    }

    public boolean create(T entity){
        return this.repository.create(entity);
    }

    public boolean update(T entity){
        return this.repository.update(entity);
    }

    public boolean delete(String id){
        return this.repository.delete(id);
    }
}
