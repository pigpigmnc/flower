package com.zsc.flower.domain.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    String content;
    Long uid;
    Long pid;
    Date createDate;
}
