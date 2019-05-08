package com.quancheng.manto.api;

import com.quancheng.common.basic.rest.Response;
import com.quancheng.manto.biz.OnlineResBiz;
import com.quancheng.manto.biz.OnlineResInfoSyncBiz;
import com.quancheng.manto.dao.OpenClientMapper;
import com.quancheng.manto.dao.RestaurantMapper;
import com.quancheng.manto.dao.UnsignedBlacklistClientMapper;
import com.quancheng.manto.dataobject.OpenClientDO;
import com.quancheng.manto.enums.RestaurantTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/restaurantUpdate")
public class RestaurantUpdateAPI {

    @Autowired
    private OpenClientMapper clientMapper;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private OnlineResBiz onlineResBiz;

    @Autowired
    private OnlineResInfoSyncBiz onlineResInfoSyncBiz;

    @Autowired
    private UnsignedBlacklistClientMapper unsignedBlacklistClientMapper;

    @ApiOperation(value = "更新当面付餐厅开放企业,list为空则不进行任何更改")
    @RequestMapping(value = "/updateUnsignedOpenClient", method = RequestMethod.POST)
    public Response updateUnsignedOpenClient(@RequestBody List<String> restaurantIds) {

        if (CollectionUtils.isEmpty(restaurantIds)) {
            return Response.success();
        }

        List<OpenClientDO> openClientList = clientMapper.unsignedOpenClientByResId(restaurantIds);
        for (OpenClientDO openClientDO : openClientList) {
            clientMapper.updateOpenClient(openClientDO.getResId(),
                    RestaurantTypeEnum.UNSIGNED.getCode(), openClientDO.getClientIds());
        }
        if (restaurantIds.size() == openClientList.size()) {
            return Response.success();
        }

        List<String> resIdsByOpenClient = openClientList.stream().map(OpenClientDO::getResId).collect(Collectors.toList());
        restaurantIds.removeAll(resIdsByOpenClient);
        clientMapper.batchUpdateOpenClientEmpty(restaurantIds, RestaurantTypeEnum.UNSIGNED.getCode());

        return Response.success();
    }

    @ApiOperation(value = "更新线上开放企业,list为空则全量更新")
    @RequestMapping(value = "/updateOnlineOpenClient", method = RequestMethod.POST)
    public Response updateOnlineOpenClient(@RequestBody List<String> restaurantIds) {

        onlineResInfoSyncBiz.syncOpenClient(restaurantIds);
        return Response.success();
    }

    @ApiOperation(value = "更新当面付餐厅信息,list为空则全量更新")
    @RequestMapping(value = "/updateUnsignedRes", method = RequestMethod.POST)
    public Response updateUnsignedRes(@RequestBody List<String> restaurantIds) {

        restaurantMapper.syncUnsignedResByResIds(restaurantIds);
        return Response.success();
    }

    @ApiOperation(value = "更新线上餐厅信息,list为空则全量更新")
    @RequestMapping(value = "/updateOnlineRes", method = RequestMethod.POST)
    public Response updateOnlineRes(@RequestBody List<String> restaurantIds) {

        onlineResBiz.syncOnlineRes(restaurantIds);
        return Response.success();
    }

    @ApiOperation(value = "更新特许餐厅信息,list为空则全量更新")
    @RequestMapping(value = "/updateSpecialRes", method = RequestMethod.POST)
    public Response updateSpecialRes(@RequestBody List<String> restaurantIds) {

        restaurantMapper.syncSpecialResByResIds(restaurantIds);
        return Response.success();
    }

    @ApiOperation(value = "更新线上餐厅黑名单企业,list为空则全量更新")
    @RequestMapping(value = "/updateOnlineBlacklistClient", method = RequestMethod.POST)
    public Response updateOnlineBlacklistClient(@RequestBody List<String> restaurantIds) {

        onlineResInfoSyncBiz.syncBlacklistClient(restaurantIds);
        return Response.success();
    }

    @ApiOperation(value = "更新当面付餐厅黑名单企业,list为空则全量更新")
    @RequestMapping(value = "/updateUnsignedBlacklistClient", method = RequestMethod.POST)
    public Response updateUnsignedBlacklistClient(@RequestBody List<String> restaurantIds) {

        unsignedBlacklistClientMapper.syncBlacklistClient(restaurantIds);
        return Response.success();
    }

}
