package com.zsc.flower.dao;

import com.zsc.flower.domain.entity.Property;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyDao extends BaseMapper<Property> {
    public int selectCountByCid(long cid);
    public int selectAddPropertyValue(@Param("pid") long pid, @Param("ptid") long ptid, @Param("value") String value);
}
