package com.zsc.flower.service.Impl;

import com.zsc.flower.dao.ProductImageDao;
import com.zsc.flower.domain.entity.ProductImage;
import com.zsc.flower.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductImageService")
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageDao productImageDao;

    @Override
    public int findInsertProductImages(ProductImage productImage) {
        return productImageDao.selectInsertProductImages(productImage);
    }

    @Override
    public List<String> findPicListByPID(long pid) {
        return productImageDao.selectPicListByPID(pid);
    }

    @Override
    public List<String> findProductImageUrlById(long id) {
        return productImageDao.selectProductImageUrlById(id);
    }

//    @Override
//    public String findIndexImageNull(long pid) {
//        return productImageDao.selectIndexImageNull(pid);
//    }
//
//    @Override
//    public String findViewImageNull(long pid) {
//        return productImageDao.selectViewImageNull(pid);
//    }
//
//    @Override
//    public int findInsertIndexImages(long pid,String indexImg) {
//        return productImageDao.selectInsertIndexImages(pid,indexImg);
//    }
////
//    @Override
//    public int findInsertViewImages(long pid, String fileurlpath) {
//        return productImageDao.selectInsertViewImages(pid,fileurlpath);
//    }
}
