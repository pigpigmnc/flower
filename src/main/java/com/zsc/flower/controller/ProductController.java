package com.zsc.flower.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.flower.domain.entity.*;
import com.zsc.flower.domain.vo.ResponseData;
import com.zsc.flower.domain.vo.ResponseImageUrl;
import com.zsc.flower.service.ProductImageService;
import com.zsc.flower.service.ProductService;
import com.zsc.flower.service.PropertyService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductService productService;

    @Autowired
    PropertyService propertyService;

    //针对产品来做图片的插入  多文件！！！！！！！！！
    @RequestMapping("/uploadPictures")
//    public ResponseImageUrl uploadPictures(@RequestParam("file") CommonsMultipartFile file) {
    public ResponseImageUrl uploadPictures(@RequestParam("pid")long pid,
                                           @RequestParam("files") MultipartFile[] files) {
        int n=0;
        String fileName=null,fileurlpath=null;

        //判断文件是否为空
        if(files.length!=0){
            for (MultipartFile file:files) {
                String uuid = UUID.randomUUID().toString().trim();
                String fileN=file.getOriginalFilename();
                int index=fileN.indexOf(".");
                fileName=uuid+fileN.substring(index);
                File fileMkdir;
                try {
//            fileMkdir=new File("F:\\mall-images");
                    fileMkdir=new File("F:\\apache-tomcat-9.0.14dir\\webapps\\ROOT\\mall-images");

                    if(!fileMkdir.exists()) {
                        fileMkdir.mkdir();
                    }
                    //定义输出流 将文件保存在F盘    file.getOriginalFilename()为获得文件的名字
                    FileOutputStream os = new FileOutputStream(fileMkdir.getPath()+"\\"+fileName);
                    InputStream in = file.getInputStream();
                    int b;
                    while((b=in.read())!=-1){ //读取文件
                        os.write(b);
                    }
                    os.flush(); //关闭流
                    in.close();
                    os.close();
                    System.out.println(fileMkdir.toString());
                } catch (Exception e) {
                    return ResponseImageUrl.createByError();
                }
                //访问路径为http://localhost:8081/+fileurlpath
                fileurlpath="mall-images/"+fileName;
                ProductImage productImage=new ProductImage();
                productImage.setPid(pid);
                productImage.setFilename(fileName);
                productImage.setFileurlpath(fileurlpath);
                n=productImageService.findInsertProductImages(productImage);
            }
            if(n==1)
                return ResponseImageUrl.createBySuccess("success",fileName,fileurlpath);
            else
                return ResponseImageUrl.createByError();
        }else{
            return ResponseImageUrl.createByError();
        }
    }

    //新增一个分类
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public ResponseData addCategory(String name){
        if(productService.findAddCategory(name)==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }
    //展示所有分类
    @RequestMapping(value = "/listCategory", method = RequestMethod.GET)
    public ResponseData listCategory(){
        List<Category> categoryList=productService.findListCategory();
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(categoryList);
        return responseData;
    }
    //对应分类下的属性，增加
    @RequestMapping(value = "/addProperty",method = RequestMethod.POST)
    public ResponseData addProperty(long cid,String name){
        if(productService.findAddProperty(cid,name)==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }
    //产品管理，增加每个产品的基础信息  无用了！！！！！！！！！！！！！！！！！！！！！！！
    @RequestMapping(value = "/addProduct",method = RequestMethod.POST)
    public ResponseData addProduct(Product product){
        //添加产品的时候，需要插入 产品表 以及 产品图片表
        //传过来的值应该有：
        //产品名，产品二级标题，原价，优惠价，库存，以及在分类管理下点进去的产品的类型ID，产品图片
        //其中product表需要的字段是，产品名，产品二级标题，原价，优惠价，库存，插入时间，类型ID
        //productimage表需要的字段是产品id(pid),图片名称，图片的存储路径
        //要先插入product表，才会产生产品id，才能插入productimage表
        product.setCreateDate(new Date());
        if(productService.findAddProduct(product)==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }

    //==============================================================
    //显示该分类下的属性
    @RequestMapping(value = "/listCategoryProperty",method = RequestMethod.GET)
    public ResponseData listCategoryProperty(long cid){
        List<Property> propertyList=productService.findListCategoryProperty(cid);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(propertyList);
        return responseData;
    }

    //编辑该产品的属性!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @RequestMapping(value = "/addProductProperty",method = RequestMethod.POST)
    public ResponseData addPropertyValue(Propertyvalue propertyvalue){
        //插入该产品的属性的时候，需要插入产品ID，该分类下的属性的ID，以及该产品对应的具体属性值
        if(productService.findAddPropertyValue(propertyvalue)==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }
    //查询商品的所有信息
    @RequestMapping(value = "/showBaseDetail",method = RequestMethod.GET)
    public ResponseData showBaseDetail(long id){
        List<BaseDetail> baseDetailList=productService.findShowBaseDetail(id);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(baseDetailList);
        return responseData;
    }
    //查询该商品的具体信息
    @RequestMapping(value = "/showExtendDetail",method = RequestMethod.GET)
    public ResponseData showExtendDetail(long id){
        List<ExtendDetail> extendDetailList=productService.findShowExtendDetail(id);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(extendDetailList);
        return responseData;
    }
    //后台的商品列表
    @RequestMapping(value = "/listProduct",method = RequestMethod.GET)
    public ResponseData listProduct(@RequestParam(defaultValue = "1",required = true,value = "pn")Integer pn){
        int pageSize=10;
        PageHelper.startPage(pn,pageSize);
        PageInfo<ListProduct> pageInfo=productService.findListProduct(pn,pageSize);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(pageInfo);
        return responseData;
    }
    //==================================
//    商品和图片一同插入表中 原版
    @RequestMapping(value = "/addProductWithPic",method = RequestMethod.POST)
    public ResponseData addProductWithPic(Product product,
                                          @RequestParam("files") MultipartFile[] files){
        product.setCreateDate(new Date());
        int n=productService.findAddProduct(product);//    这里插入了一个product

        int m=0;
        long pid=0;
        ResponseData responseData;
        if(n==1) {
            pid=productService.findPidByTopInsert();
            if(files.length!=0) {
                for (MultipartFile file : files) {
                    ProductImage productImage = HandlePic(file);
                    productImage.setPid(pid);
                    m = productImageService.findInsertProductImages(productImage);//    这里插入了一个productimage
                }
            }else{
                return ResponseData.createByError();
            }
        }
        if(m==1){
            responseData=ResponseData.createBySuccess();
            responseData.setData(pid);
            return responseData;
        }
        else{
            responseData=ResponseData.createByError();
            return responseData;
        }
    }

    private ProductImage HandlePic(MultipartFile file){
        int n=0;
        String fileName=null,fileurlpath=null;

        String uuid = UUID.randomUUID().toString().trim();
        String fileN=file.getOriginalFilename();
        int index=fileN.indexOf(".");
        fileName=uuid+fileN.substring(index);
        File fileMkdir;

        try {
            fileMkdir=new File("F:\\apache-tomcat-9.0.14dir\\webapps\\ROOT\\mall-images");

            if(!fileMkdir.exists()) {
                fileMkdir.mkdir();
            }
            //定义输出流 将文件保存在F盘    file.getOriginalFilename()为获得文件的名字
            FileOutputStream os = new FileOutputStream(fileMkdir.getPath()+"\\"+fileName);
            InputStream in = file.getInputStream();
            int b;
            while((b=in.read())!=-1){ //读取文件
                os.write(b);
            }
            os.flush(); //关闭流
            in.close();
            os.close();
            System.out.println(fileMkdir.toString());
        } catch (Exception e) {
            System.out.println("图片上传失败！");
        }
        fileurlpath="mall-images/"+fileName;
        ProductImage productImage=new ProductImage();
        productImage.setFilename(fileName);
        productImage.setFileurlpath(fileurlpath);
        return productImage;
    }

    @RequestMapping(value = "/addPropertyValue",method = RequestMethod.POST)
    public ResponseData addPropertyValue(@RequestParam("pid")long pid, @RequestParam("arr") String arr){

        System.out.println(arr);
        //根据产品ID获取它的种类，然后数出这个种类下的属性有多少条，有多少条就循环多少次
        //要传入产品的ID值，现在模拟已经传入了，产品ID为25
//        long pid=25;
        int n=0;
        JSONArray jsonArray = JSONArray.fromObject(arr);
        for(int i=0;i<jsonArray.size();i++){
            long id=Long.parseLong((String) jsonArray.getJSONObject(i).get("id"));
            String name=(String) jsonArray.getJSONObject(i).get("name");
            System.out.println(id+","+name);
            n=propertyService.findAddPropertyValue(pid,id,name);
        }
        if(n==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }
    //按分类ID,返回listProduct列表yi
    @RequestMapping("/listProductByCId")
    public ResponseData listProductByCId(@RequestParam("cid")long cid){
        List<ListProduct> listProductList=productService.findProductsByCId(cid);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(listProductList);
        responseData.setMsg("success");
        return responseData;
    }

    //单个商品的展示，返回商品的基本信息加五张图
    @RequestMapping(value = "/showProductDetail",method = RequestMethod.GET)
    public ResponseData showProductDetail(@RequestParam("pid")long pid){
        //这个SimpleProWithPic用来打包所有商品信息传递给前端‘
        SimpleProWithPic simpleProWithPic=new SimpleProWithPic();

        List<String> fileurlpathlist=productImageService.findPicListByPID(pid);
        simpleProWithPic.setFileurlpath(fileurlpathlist);

        SimpleDetail simpleDetail=productService.findSimpleDetail(pid);
        simpleProWithPic.setSimpleDetail(simpleDetail);


        List<SimpleProperty> simpleProperty=productService.findSimpleProperty(pid);
        simpleProWithPic.setSimpleProperty(simpleProperty);

        ResponseData responseData=ResponseData.createBySuccess();

        responseData.setData(simpleProWithPic);
        return responseData;
    }

    //按商品名模糊匹配查询出产品列表
    @RequestMapping(value = "/dimSearch",method = RequestMethod.GET)
    public ResponseData dimSearch(@RequestParam("name")String name){
        List<ListProduct> listProductList=productService.findListProductByDimSearch(name);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(listProductList);
        return responseData;
    }

    //产品的库存、价格、商品名、二级标题修改
    @RequestMapping(value = "/modifyProduct",method = RequestMethod.GET)
    public ResponseData modifyProduct(Product product){
        //需要提供商品的ID
        long id=product.getId();
        Product newProduct=productService.findProductById(id);
        newProduct.setStock(product.getStock());
        newProduct.setPromotePrice(product.getPromotePrice());
        newProduct.setName(product.getName());
        newProduct.setSubTitle(product.getSubTitle());
        if(productService.findUpdateProduct(product)==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }

    //后台商品下架
    @RequestMapping(value = "/deleteProduct",method = RequestMethod.GET)
    public ResponseData deleteProduct(@Param("id")long id){
        productService.closeforeign();
        if(productService.findDeleteProduct(id)==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }
    //前台按createDate排序返回商品展示列表
    @RequestMapping(value = "/showProductByCreateDate",method = RequestMethod.GET)
    public ResponseData showProductByCreateDate(){
        List<ListProduct> listProductList=productService.findListProductByCreateDate();
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(listProductList);
        return responseData;
    }
    //前台按createDate排序返回商品展示列表
    @RequestMapping(value = "/showProductBySaleCount",method = RequestMethod.GET)
    public ResponseData showProductBySaleCount(){
        List<ListProduct> listProductList=productService.findListProductBySaleCount();
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(listProductList);
        return responseData;
    }

    //单个商品的展示，返回商品的基本信息加单张图
    @RequestMapping(value = "/showProductDetailWithSingle",method = RequestMethod.GET)
    public ResponseData showProductDetailWithSingle(@RequestParam("pid")long pid){
        //这个SimpleProWithPic用来打包所有商品信息传递给前端‘
        SimpleProWithSinglePic simpleProWithSinglePic=new SimpleProWithSinglePic();

        List<String> fileurlpathlist=productImageService.findPicListByPID(pid);

        simpleProWithSinglePic.setFileurlpath(fileurlpathlist.get(0));

        SimpleDetail simpleDetail=productService.findSimpleDetail(pid);
        simpleProWithSinglePic.setSimpleDetail(simpleDetail);


        List<SimpleProperty> simpleProperty=productService.findSimpleProperty(pid);
        simpleProWithSinglePic.setSimpleProperty(simpleProperty);

        ResponseData responseData=ResponseData.createBySuccess();

        responseData.setData(simpleProWithSinglePic);
        return responseData;
    }

    //base64转换
    @RequestMapping(value = "/base64",method = RequestMethod.POST)
    public ResponseData base64(Product product,@RequestParam("dataBase") String arr){

        product.setSaleCount(15L);
        product.setCreateDate(new Date());
        int n=productService.findAddProduct(product);//    这里插入了一个product

        int m=0;
        long pid=0;
        ResponseData responseData;
        JSONArray jsonArray = JSONArray.fromObject(arr);
        if(n==1) {      //说明product已经插入到数据库里头
            pid=productService.findPidByTopInsert();//通过创建时间把产品的ID取出来
            for(int i=0;i<jsonArray.size();i++){
                String data=(String) jsonArray.getJSONObject(i).get("name");
                ProductImage productImage = parseContents(data);
                productImage.setPid(pid);
                m = productImageService.findInsertProductImages(productImage);//    这里插入了一个productimage
            }
        }
        if(m==1){
            responseData=ResponseData.createBySuccess();
            responseData.setData(pid);
            return responseData;
        }
        else{
            responseData=ResponseData.createByError();
            return responseData;
        }
    }

    public ProductImage parseContents(String contents) {
        String fileNameSuffix=null;
        if (contents.indexOf("data:image/") != -1) {
            int firstIndex = contents.indexOf("data:image/") + 11;

            int index1 = contents.indexOf(";base64,");

            String type = contents.substring(firstIndex, index1);

            fileNameSuffix=UUID.randomUUID().toString() + "." + type;
            String fileName="F:\\apache-tomcat-9.0.14dir\\webapps\\ROOT\\mall-images\\"+fileNameSuffix;
            BASE64Decoder decoder = new BASE64Decoder();
            OutputStream os = null;
            try {
                String imgsrc = StringUtils.substringBefore(contents.substring(contents.indexOf(";base64,") + 8), "\"");
                byte[] bytes = decoder.decodeBuffer(imgsrc);
                //替换之前的src中base64数据为servlet请求
                contents = contents.replace("data:image/" + type + ";base64," + imgsrc, "/images.do?src=" + fileName);

                File file = new File(fileName);
                //获取父目录
                File fileParent = file.getParentFile();
                //判断是否存在
                if (!fileParent.exists()) {
                    //创建父目录文件
                    fileParent.mkdirs();
                }
                file.createNewFile();
                os = new FileOutputStream(file);
                os.write(bytes);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//        return fileNameSuffix;
        String path="mall-images/"+fileNameSuffix;
        ProductImage productImage=new ProductImage();
        productImage.setFilename(fileNameSuffix);
        productImage.setFileurlpath(path);
        return productImage;

    }

    @GetMapping("/getSort")
    public ResponseData getSort(@RequestParam(defaultValue = "1",required = true,value="pn")Integer pn,
                                @RequestParam("cid") long cid, @RequestParam("sort")String sort){

        int pageSize=10;
        PageHelper.startPage(pn,pageSize);
        try {
        PageInfo<ListProduct> pageInfo=productService.findSort(pn,pageSize,cid,sort);
            ResponseData responseData=  ResponseData.createBySuccess();
            responseData.setMsg("ok");
            responseData.setData(pageInfo);
            return responseData;
        }catch (Exception e) {
            e.printStackTrace();
            ResponseData responseData=  ResponseData.createByError();
            responseData.setMsg("系统出错");
            return responseData;
        }
    }
    @GetMapping("/getReview")
    public ResponseData getReviewByPid(@RequestParam("pid") long pid)
    {
        try {
            List<Review> listReview=productService.findReviewByPid(pid);
            ResponseData responseData=  ResponseData.createBySuccess();
            responseData.setMsg("ok");
            responseData.setData(listReview);
            return responseData;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ResponseData responseData=  ResponseData.createByError();
            responseData.setMsg("系统出错");
            return responseData;
        }
    }

    //压缩上传图片
//    @RequestMapping(value = "/compressImage",method = RequestMethod.POST)
//    public ResponseImageUrl compressImage(@RequestParam("pid")long pid,
//                                      @RequestParam("file") CommonsMultipartFile file){
//        //先判断依次判断该商品的主图是否为空，空的话就插入主图，不空就插入别的图
//        int flag=0;
//        if(productImageService.findIndexImageNull(pid)==null) flag=1;
//        else if(productImageService.findViewImageNull(pid)==null) flag=2;
//        //重新分配图片名字，免得用户上传重复
//        String uuid = UUID.randomUUID().toString().trim();
//        String fileN=file.getOriginalFilename();
//        int index=fileN.indexOf(".");
//        String fileName=uuid+fileN.substring(index);
//        File fileMkdir;
//
//        String afileName,pre=null;
//        int i=0,i1=0,n=0;
//
//        switch (flag){
//            case 1: {
//                i = 268;
//                i1=335;
//                pre="index";
//                break;
//            }
//            case 2:{
//                i = 340;
//                i1=382;
//                pre="view";
//                break;
//            }
//            default:
//                break;
//        }
//        //开始进行图片存储和转换操作
//        try {
//            fileMkdir=new File("F:\\apache-tomcat-9.0.14dir\\webapps\\ROOT\\mall-images\\compressbefore");
//
//            if(!fileMkdir.exists()) {
//                fileMkdir.mkdir();
//            }
//            //定义输出流 将文件保存在F盘    file.getOriginalFilename()为获得文件的名字
//            FileOutputStream os = new FileOutputStream(fileMkdir.getPath()+"\\"+fileName);
//            InputStream in = file.getInputStream();
//            int b;
//            while((b=in.read())!=-1){ //读取文件
//                os.write(b);
//            }
//            os.flush(); //关闭流
//            in.close();
//            os.close();
//
//            afileName=pre+fileName;
//            String combefore="F:\\apache-tomcat-9.0.14dir\\webapps\\ROOT\\mall-images\\compressbefore\\"+fileName;
//            String comafter="F:\\apache-tomcat-9.0.14dir\\webapps\\ROOT\\mall-images\\compressafter\\"+afileName;
//
//            Thumbnails.of(combefore)
////                    .scale(1f)
////                    .outputQuality(0.5f)
//                    .size(i,i1)
//                    .toFile(comafter);
//
//        } catch (Exception e) {
//            return ResponseImageUrl.createByError();
//        }
//
//
//        String fileurlpath="mall-images/compressafter/"+afileName;
//        switch (flag){
//            case 1:{
//                n=productImageService.findInsertIndexImages(pid,fileurlpath);
//                break;
//            }
//            case 2:{
////                n=productImageService.findInsertViewImages(pid,fileurlpath);
//                break;
//            }
//            default:
//                break;
//        }
//        if(n==1)
//            return ResponseImageUrl.createBySuccess();
////            return ResponseImageUrl.createBySuccess("success",fileName,fileurlpath);
//        else
//            return ResponseImageUrl.createByError();
//    }
}
