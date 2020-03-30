package com.zsc.flower.dao;

import com.zsc.flower.domain.entity.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewDao extends BaseMapper<Review> {
   /* public void addRevie();
    public void deleteReviewByPidAndUid();*/
    public List<Review> selectRevieByPid(@Param("pid") long pid);
}
