package com.quancheng.manto.dao;

import com.quancheng.manto.dataobject.OpenClientDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author zz 2018/12/10
 */
@Mapper
public interface OpenClientMapper {

    List<String> unsignedOpenClientResIds(@Param("startTime") Date startTime);

    List<OpenClientDO> unsignedOpenClientByResId(@Param("resIds") List<String> resIds);

    void updateOpenClient(@Param("resId") String resId, @Param("type") String type, @Param("openClient") String openClient);

    void batchUpdateOpenClientEmpty(@Param("resIds") List<String> resIds, @Param("type") String type);
}
