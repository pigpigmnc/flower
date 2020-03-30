package com.zsc.flower.domain.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "propertyvalue")
public class Propertyvalue {
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    Long pid;
    Long ptid;
    String value;
}
