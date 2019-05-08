package com.quancheng.manto.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zz 2018/12/29
 */
@Component
public class OnlineResBiz {

    @Autowired
    private OnlineResInfoSyncBiz resInfoSyncBiz;

    public void syncOnlineRes(List<String> resIds) {

        resInfoSyncBiz.syncResMainInfo(resIds);
        resInfoSyncBiz.syncReservationInfo(resIds);
        resInfoSyncBiz.syncTakeawayInfo(resIds);
        resInfoSyncBiz.syncSettlementInfo(resIds);
        resInfoSyncBiz.syncOpenClient(resIds);
        resInfoSyncBiz.syncBlacklistClient(resIds);
        resInfoSyncBiz.syncHitNum(resIds);
        resInfoSyncBiz.syncIndoorAndOutdoorUrls(resIds);
    }
}
