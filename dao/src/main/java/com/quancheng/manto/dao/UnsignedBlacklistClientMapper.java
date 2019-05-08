package com.quancheng.manto.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zz 2018/12/27
 */
@Mapper
public interface UnsignedBlacklistClientMapper {

    void syncBlacklistClient(@Param("resIds") List<String> resIds);
}
