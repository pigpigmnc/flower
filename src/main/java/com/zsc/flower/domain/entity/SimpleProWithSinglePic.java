package com.zsc.flower.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class SimpleProWithSinglePic {
    SimpleDetail simpleDetail;//该类囊括所有的信息
    String fileurlpath;//这里存储该商品的所有图片路径
    List<SimpleProperty> simpleProperty;//该类囊括该商品的所有的属性以及属性值
}
