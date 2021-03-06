package com.zsc.flower.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.flower.dao.ProductDao;
import com.zsc.flower.domain.entity.*;
import com.zsc.flower.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public int findAddProduct(Product product) {
        return productDao.selectAddProduct(product);
    }
    //增加种类
    @Override
    public int findAddCategory(String name) {
        return productDao.selectAddCategory(name);
    }
    //展示种类
    @Override
    public List<Category> findListCategory() {
        return productDao.selectListCategory();
    }
    //增加对应分类下的属性
    @Override
    public int findAddProperty(long cid, String name) {
        return productDao.selectAddProperty(cid,name);
    }
    @Override
    public List<Property> findListCategoryProperty(long cid) {
        return productDao.selectListCategoryProperty(cid);
    }

    @Override
    public int findAddPropertyValue(Propertyvalue propertyvalue) {
        return productDao.selectAddPropertyValue(propertyvalue);
    }

    @Override
    public List<BaseDetail> findShowBaseDetail(long id) {
        return productDao.selectShowBaseDetail(id);
    }

    @Override
    public List<ExtendDetail> findShowExtendDetail(long id) {
        return productDao.selectShowExtendDetail(id);
    }

    @Override
    public PageInfo<ListProduct> findListProduct(int page, int size) {
        PageHelper.startPage(page,size);
        PageHelper.orderBy("id desc");
        PageInfo<ListProduct> pageInfo=new PageInfo<>(productDao.selectListProduct());
        return pageInfo;
    }
//=========================================
    @Override
    public int findCategoryCount(long cid) {
        return productDao.selectCategoryCount(cid);
    }

    @Override
    public List<Property> findListProperty(long cid) {
        return productDao.selectListProperty(cid);
    }


    @Override
    public long findOldStock(long id) {
        return productDao.selectOldStock(id);
    }

    @Override
    public int findUpdateStock(long id, long stock) {
        return productDao.selectUpdateStock(id,stock);
    }

    @Override
    public long findOldSaleCount(long id) {
        return productDao.selectOldSaleCount(id);
    }

    @Override
    public int findUpdateSaleCount(long id, long stock) {
        return productDao.selectUpdateSaleCount(id,stock);
    }

    @Override
    public List<ListProduct> findProductsByCId(long cid) {
        return productDao.selectProductByCId(cid);
    }

    @Override
    public SimpleDetail findSimpleDetail(long id) {
        return productDao.selectSimpleDetail(id);
    }

    @Override
    public List<SimpleProperty> findSimpleProperty(long pid) {
        return productDao.selectSimpleProperty(pid);
    }

    @Override
    public List<ListProduct> findListProductByDimSearch(String name) {
        return productDao.selectListProductByDimSearch(name);
    }

    @Override
    public Product findProductById(long id) {
        return productDao.selectProductById(id);
    }

    @Override
    public int findUpdateProduct(Product product) {
        return productDao.selectUpdateProduct(product);
    }

    @Override
    public int findDeleteProduct(long id) {

        return productDao.selectDeleteProduct(id);
    }

    @Override
    public void closeforeign() {
        productDao.closeforeign();
    }

    @Override
    public List<ListProduct> findListProductByCreateDate() {
        return productDao.selectListProductByCreateDate();
    }

    @Override
    public List<ListProduct> findListProductBySaleCount() {
        return productDao.selectListProductBySaleCount();
    }

    @Override
    public PageInfo<ListProduct> findSort(int page, int size, long cid, String sort) {
        PageHelper.startPage(page,size);
        switch (sort)
        {
            case "saleCount":{
                PageHelper.orderBy("saleCount desc");
                PageInfo<ListProduct> pageInfo=new PageInfo<>(productDao.selectProductByCId(cid));
                return pageInfo;
            }
            case "createDate":{
                PageHelper.orderBy("createDate desc");
                PageInfo<ListProduct> pageInfo=new PageInfo<>(productDao.selectProductByCId(cid));
                return pageInfo;
            }
            case "originalPrice":{
                PageHelper.orderBy("originalPrice desc");
                PageInfo<ListProduct> pageInfo=new PageInfo<>(productDao.selectProductByCId(cid));
                return pageInfo;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public List<Review> findReviewByPid(long pid) {
        return productDao.selectReviewByPid(pid);
    }

    @Override
    public long findPidByTopInsert() {
        return productDao.findPidByTopInsert();
    }

//    public List<ListProduct> findProductsByCId(long cid){
//        return productDao.selectProductByCId(cid);
//    }
}
