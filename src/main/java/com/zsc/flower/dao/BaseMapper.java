package com.zsc.flower.dao;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 说明：扩展其他的基类Mapper
 * @param <T>
 */
public interface BaseMapper<T> extends Mapper<T>,
        MySqlMapper<T>, IdsMapper<T>, ConditionMapper<T> {
}
