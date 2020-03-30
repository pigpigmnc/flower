package com.zsc.flower.service;

import com.zsc.flower.domain.entity.ProductImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductImageService {
    public int findInsertProductImages(ProductImage productImage);
    //2019-7-13 10:00
    public List<String> findPicListByPID(long pid);

    public List<String> findProductImageUrlById(long id);
//    public String findIndexImageNull(long pid);
//    public String findViewImageNull(long pid);
////
//    public int findInsertIndexImages(long pid,String indexImg);
//    public int findInsertViewImages(long pid,String fileurlpath);
}
