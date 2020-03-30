package com.zsc.flower.service.Impl;

import com.zsc.flower.dao.ReviewDao;
import com.zsc.flower.domain.entity.Review;
import com.zsc.flower.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewDao reviewDao;

    @Override
    public List<Review> findReviewByPid(long pid) {
        return reviewDao.selectRevieByPid(pid);
    }
}
