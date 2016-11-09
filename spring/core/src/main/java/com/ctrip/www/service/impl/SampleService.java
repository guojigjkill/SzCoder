package com.ctrip.www.service.impl;

import com.ctrip.www.entity.Sample;
import com.ctrip.www.platform.context.IContextRepository;
import com.ctrip.www.platform.repository.IRepositoryFactory;
import com.ctrip.www.platform.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by wang.na on 2016/11/7.
 */
@Service
public class SampleService extends BaseService<Sample> {
    @Autowired
    public SampleService(IRepositoryFactory repositoryFactory, IContextRepository contextRepository) {
        super(repositoryFactory, contextRepository, "db", Sample.class);
    }
}
