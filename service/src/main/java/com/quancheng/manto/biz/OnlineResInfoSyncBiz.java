package com.quancheng.manto.biz;

import com.quancheng.manto.dao.OnlineResExtraInfoMapper;
import com.quancheng.manto.dao.RestaurantMapper;
import com.quancheng.manto.dataobject.PicDO;
import com.quancheng.manto.dataobject.ResIndoorOutdoorDO;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zz 2018/12/29
 */
@Component
public class OnlineResInfoSyncBiz {

    private static final QcLog LOGGER = LogUtil.getLogger(OnlineResInfoSyncBiz.class);

    @Autowired
    OnlineResExtraInfoMapper resExtraInfoMapper;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Async
    public void syncResMainInfo(List<String> resIds) {
        Date start = new Date();
        LOGGER.warn("syncResMainInfo start:{}", start);
        try {
            restaurantMapper.syncOnlineResByResIds(resIds);
        } catch (Exception e) {
            LOGGER.error("同步线上餐厅主信息异常", e);
        }
        LOGGER.warn("syncResMainInfo end:{}", new Date().getTime() - start.getTime());
    }

    @Async
    public void syncReservationInfo(List<String> resIds) {
        Date start = new Date();
        LOGGER.warn("syncReservationInfo start:{}", start);
        try {
            resExtraInfoMapper.syncReservationInfo(resIds);
        } catch (Exception e) {
            LOGGER.error("同步线上餐厅预定信息异常", e);
        }
        LOGGER.warn("syncReservationInfo end:{}", new Date().getTime() - start.getTime());
    }

    @Async
    public void syncTakeawayInfo(List<String> resIds) {
        Date start = new Date();
        LOGGER.warn("syncTakeawayInfo start:{}", start);
        try {
            resExtraInfoMapper.syncTakeawayInfo(resIds);
        } catch (Exception e) {
            LOGGER.error("同步线上餐厅外卖信息异常", e);
        }
        LOGGER.warn("syncTakeawayInfo end:{}", new Date().getTime() - start.getTime());
    }

    @Async
    public void syncSettlementInfo(List<String> resIds) {
        Date start = new Date();
        LOGGER.warn("syncSettlementInfo start:{}", start);
        try {
            resExtraInfoMapper.syncSettlementInfo(resIds);
        } catch (Exception e) {
            LOGGER.error("同步线上餐厅结算信息异常", e);
        }
        LOGGER.warn("syncSettlementInfo end:{}", new Date().getTime() - start.getTime());

    }

    @Async
    public void syncOpenClient(List<String> resIds) {
        Date start = new Date();
        LOGGER.warn("syncOpenClient start:{}", start);
        try {
            resExtraInfoMapper.syncOpenClient(resIds);
        } catch (Exception e) {
            LOGGER.error("同步线上餐厅开放企业信息异常", e);
        }
        LOGGER.warn("syncOpenClient end:{}", new Date().getTime() - start.getTime());

    }

    @Async
    public void syncBlacklistClient(List<String> resIds) {
        Date start = new Date();
        LOGGER.warn("syncBlacklistClient start:{}", start);
        try {
            resExtraInfoMapper.syncBlacklistClient(resIds);
        } catch (Exception e) {
            LOGGER.error("同步线上餐厅黑名单企业信息异常", e);
        }
        LOGGER.warn("syncBlacklistClient end:{}", new Date().getTime() - start.getTime());

    }

    @Async
    public void syncHitNum(List<String> resIds) {
        Date start = new Date();
        LOGGER.warn("syncHitNum start:{}", start);
        try {
            resExtraInfoMapper.syncHitNum(resIds);
        } catch (Exception e) {
            LOGGER.error("同步线上餐厅点击量信息异常", e);
        }
        LOGGER.warn("syncHitNum end:{}", new Date().getTime() - start.getTime());

    }

    @Async
    public void syncIndoorAndOutdoorUrls(List<String> resIds) {
        Date start = new Date();
        LOGGER.warn("syncIndoorAndOutdoorUrls start:{}", start);

        List<ResIndoorOutdoorDO> resDoors;
        try {
            resDoors = resExtraInfoMapper.selectOnlineDoors(resIds);
        } catch (Exception e) {
            LOGGER.error("查询线上餐厅indoor异常", e);
            return;
        }

        Map<String, String> picMap = getPicMap(resDoors);
        if (picMap.size() == 0) {
            return;
        }

        for (ResIndoorOutdoorDO resDoor : resDoors) {
            resDoor.setId("online" + resDoor.getId());
            resDoor.setOutdoor(picMap.get(resDoor.getOutdoor()));

            String indoor = resDoor.getIndoor();
            if (StringUtils.isEmpty(indoor)) {
                continue;
            }
            String[] indoors = indoor.split(",");
            StringJoiner joiner = new StringJoiner(",");
            for (int i = 0; i < indoors.length; i++) {
                String picId = indoors[i];
                String url = picMap.get(picId);
                if (StringUtils.isEmpty(url)) {
                    continue;
                }
                joiner.add(url);
            }

            resDoor.setIndoor(joiner.toString());
        }

        try {
            resExtraInfoMapper.syncOnlineDoorUrls(resDoors);
        } catch (Exception e) {
            LOGGER.error("同步搜索线上餐厅indoorUrls信息异常", e);
        }
        LOGGER.warn("syncIndoorAndOutdoorUrls end:{}", new Date().getTime() - start.getTime());

    }

    private Map<String, String> getPicMap(List<ResIndoorOutdoorDO> resDoors) {

        Map<String, String> picMap = new HashMap<>();
        if (CollectionUtils.isEmpty(resDoors)) {
            return picMap;
        }

        List<String> picIds = new ArrayList<>();
        for (ResIndoorOutdoorDO resDoor : resDoors) {
            String indoor = resDoor.getIndoor();
            String outdoor = resDoor.getOutdoor();
            if (!StringUtils.isEmpty(outdoor)) {
                picIds.add(outdoor);
            }
            if (StringUtils.isEmpty(indoor)) {
                continue;
            }
            picIds.addAll(Arrays.asList(indoor.split(",")));
        }
        if (CollectionUtils.isEmpty(picIds)) {
            return picMap;
        }

        List<PicDO> pics;
        try {
            pics = resExtraInfoMapper.selectOnlinePics(picIds);
        } catch (Exception e) {
            LOGGER.error("查询线上餐厅indoor picture异常", e);
            return picMap;
        }

        if (CollectionUtils.isEmpty(pics)) {
            return picMap;
        }
        for (PicDO pic : pics) {
            picMap.put(pic.getId(), pic.getUrl());
        }

        return picMap;
    }

}
