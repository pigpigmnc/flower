package com.zsc.flower.service;

import com.zsc.flower.domain.entity.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    public List<Review> findReviewByPid(long pid);
}
