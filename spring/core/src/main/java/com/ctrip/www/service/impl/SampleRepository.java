package com.ctrip.www.service.impl;

import com.ctrip.www.entity.Sample;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wang.na on 2016/11/8.
 */
public interface SampleRepository extends MongoRepository<Sample, String> {
}
