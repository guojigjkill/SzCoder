package com.ctrip.www.service.impl;

import com.ctrip.www.entity.Sample;
import com.ctrip.www.platform.context.IContext;
import com.ctrip.www.platform.context.IContextRepository;
import com.ctrip.www.platform.repository.IRepository;
import com.ctrip.www.platform.repository.IRepositoryFactory;
import com.ctrip.www.platform.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Created by wang.na on 2016/11/7.
 */
@Component
public class SampleService extends BaseService<Sample> {
    @Autowired
    SampleRepository repository;

    @Autowired
    public SampleService(IRepositoryFactory repositoryFactory, IContextRepository contextRepository) {
        super(repositoryFactory, contextRepository, "db", "collection");
    }
}
