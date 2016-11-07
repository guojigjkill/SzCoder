package com.ctrip.www.service.impl;

import com.ctrip.www.entity.Sample;
import com.ctrip.www.platform.context.IContextRepository;
import com.ctrip.www.platform.repository.IRepository;
import com.ctrip.www.platform.repository.IRepositoryFactory;
import com.ctrip.www.platform.service.BaseService;
import org.springframework.stereotype.Component;

/**
 * Created by wang.na on 2016/11/7.
 */
@Component
public class SampleService extends BaseService<Sample> {
    public SampleService(IRepositoryFactory repositoryFactory, IContextRepository contextRepository) {
        super(repositoryFactory, contextRepository, "dbName", "collectionName");
    }
}
