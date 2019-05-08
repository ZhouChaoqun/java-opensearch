package com.quancheng.manto.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author zz 2018/12/11
 */
@Mapper
public interface RestaurantMapper {

    int delete(@Param("resIds") List<String> resIds, @Param("type") String type);

    void syncAllUnsignedRes();

    void syncUnsignedResByTime(@Param("startTime") Date startTime);

    void syncUnsignedResByResIds(@Param("resIds") List<String> resIds);

    List<String> unsignedResIdsNeedToDelete();

    void syncOnlineResByResIds(@Param("resIds") List<String> resIds);

    List<String> onlineResIdsNeedToDelete();

    String existSourceOnlineRes(String resId);

    void syncSpecialResByResIds(@Param("resIds") List<String> resIds);

    List<String> specialResIdsNeedToDelete();
}
