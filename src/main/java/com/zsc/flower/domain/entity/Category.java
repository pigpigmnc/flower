package com.zsc.flower.domain.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    String name;
}
