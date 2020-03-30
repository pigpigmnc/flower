package com.zsc.flower.service;

import org.springframework.stereotype.Service;

@Service
public interface PropertyService {
    public int findCountByCid(long cid);
    public int findAddPropertyValue(long pid, long ptid, String value);
}
