package com.zsc.flower.domain.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    Long cid;
    String name;
}
