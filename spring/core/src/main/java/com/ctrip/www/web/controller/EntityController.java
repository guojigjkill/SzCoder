package com.ctrip.www.web.controller;

import com.ctrip.www.entity.Sample;
import com.ctrip.www.platform.crud.db.IDbCollection;
import com.ctrip.www.platform.crud.db.IDb;
import com.ctrip.www.platform.crud.db.IDbFactory;
import com.ctrip.www.platform.entity.IEntity;
import com.ctrip.www.platform.repository.IRepositoryFactory;
import com.ctrip.www.service.impl.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wang.na on 2016/11/7.
 */
@Controller
@RequestMapping("/entity/{dbName}/{collectionName}")
public class EntityController {
    private IRepositoryFactory repositoryFactory;
    private IDbFactory dbFactory;
    private SampleService sampleService;

    @Autowired
    public EntityController(IRepositoryFactory repositoryFactory, IDbFactory dbFactory, SampleService sampleService){
        this.dbFactory = dbFactory;
        this.repositoryFactory = repositoryFactory;
        this.sampleService = sampleService;
    }

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody
    IEntity get(@PathVariable String dbName, @PathVariable String collectionName, @RequestParam(value="id", required=true) String id) {
        Sample sample = new Sample("1");
        sampleService.create(sample);
        return  sampleService.get("hello");
    }

    @RequestMapping(method=RequestMethod.PUT)
    public @ResponseBody boolean put(@PathVariable String db, @PathVariable String collection, IEntity entity) {
        return true;
    }

    @RequestMapping(method=RequestMethod.DELETE)
    public @ResponseBody boolean delete(@PathVariable String db, @PathVariable String collection, @RequestParam(value="id", required=true) String id) {
        return true;
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody boolean post(@PathVariable String db, @PathVariable String collection, IEntity entity) {
        return true;
    }
}
