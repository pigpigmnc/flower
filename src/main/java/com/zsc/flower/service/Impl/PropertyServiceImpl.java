package com.zsc.flower.service.Impl;

import com.zsc.flower.dao.PropertyDao;
import com.zsc.flower.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    PropertyDao propertyDao;

    @Override
    public int findCountByCid(long cid) {
        return propertyDao.selectCountByCid(cid);
    }

    @Override
    public int findAddPropertyValue(long pid, long ptid, String value) {
        return propertyDao.selectAddPropertyValue(pid,ptid,value);
    }
}
