package com.quancheng.manto.dao;

import com.quancheng.manto.dataobject.PicDO;
import com.quancheng.manto.dataobject.ResIndoorOutdoorDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zz 2018/12/29
 */
public interface OnlineResExtraInfoMapper {

    void syncReservationInfo(@Param("resIds") List<String> resIds);

    void syncTakeawayInfo(@Param("resIds") List<String> resIds);

    void syncSettlementInfo(@Param("resIds") List<String> resIds);

    void syncOpenClient(@Param("resIds") List<String> resIds);

    void syncBlacklistClient(@Param("resIds") List<String> resIds);

    void syncHitNum(@Param("resIds") List<String> resIds);

    List<ResIndoorOutdoorDO> selectOnlineDoors(@Param("resIds") List<String> resIds);

    List<PicDO> selectOnlinePics(List<String> ids);

    void syncOnlineDoorUrls(@Param("doors") List<ResIndoorOutdoorDO> doors);
}
