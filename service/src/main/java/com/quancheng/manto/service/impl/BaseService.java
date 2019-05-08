package com.quancheng.manto.service.impl;

import com.google.gson.Gson;
import com.quancheng.manto.model.ResponseStatus;
import com.quancheng.manto.model.basemodel.BaseResponse;
import com.quancheng.manto.model.basemodel.PageRequest;
import com.quancheng.manto.model.basemodel.PageResponse;

import java.lang.reflect.Method;

/**
 * @author zz 2018/11/14
 */
public class BaseService {

    public static final Gson GSON = new Gson();

    private static final int OK = 0;

    PageRequest initPageRequest(PageRequest pageRequest) {
        if (pageRequest == null) {
            pageRequest = new PageRequest();
            pageRequest.setPageSize(10);
            pageRequest.setCurrentPage(1);
        } else {
            Integer currentPage = pageRequest.getCurrentPage();
            if (currentPage == null) {
                currentPage = 1;
            }
            Integer pageSize = pageRequest.getPageSize();
            if (pageSize == null || pageSize == 0) {
                pageSize = 10;
            }

            //currentPage为0则最多搜索100
            if (currentPage <= 0) {
                pageRequest.setCurrentPage(1);
                pageSize = 100;
            } else {
                pageRequest.setCurrentPage(currentPage);
            }

            pageRequest.setPageSize(pageSize);

        }
        return pageRequest;
    }

    BaseResponse buildBaseOKResponse() {
        return buildBaseResponse(OK, "OK");
    }

    BaseResponse buildBaseResponse(Integer responseStatus, String message) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(responseStatus == 0);
        baseResponse.setMessage(message);
        return baseResponse;
    }

    PageResponse buildPageResopnse(int currPge, long total, int totalPage) {
        PageResponse response = new PageResponse();
        response.setTotalPages(totalPage);
        response.setCurrentPage(currPge);
        response.setTotalAmount(total);
        return response;
    }

    <T> T buildPagedResponse(Class<T> clazz, int curPage, long total, int totalPage, Object data) {
        T response = null;
        try {
            response = clazz.newInstance();
            Method[] methods = response.getClass().getDeclaredMethods();
            for(Method method : methods) {
                String methodName = method.getName();
                if (methodName.startsWith("set")){
                    if (methodName.equals("setBase")) {
                        method.invoke(response, buildBaseResponse(ResponseStatus.SUCCESS,""));
                    } else if (methodName.equals("setPageResponse")) {
                        method.invoke(response, buildPageResopnse(curPage, total, totalPage));
                    } else {
                        method.invoke(response, data);
                    }
                }
            }
        } catch (Exception e) {
            return response;
        }

        return response;
    }

    <T> Object getObject(Class<T> clazz, BaseResponse baseResponse) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Method setBaseMethod = obj.getClass().getDeclaredMethod("setBase", BaseResponse.class);
            setBaseMethod.invoke(obj, baseResponse);
        } catch (Exception e) {
            return obj;
        }
        return obj;
    }

    <T> T buildResponse(Class<T> clazz, Integer responseStatus) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(responseStatus.equals(ResponseStatus.SUCCESS));
        baseResponse.setErrorCode(responseStatus);

        return (T) getObject(clazz, baseResponse);
    }

    <T> T buildResponse(Class<T> clazz, Integer responseStatus, String message) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(responseStatus.equals(ResponseStatus.SUCCESS));
        baseResponse.setMessage(message);
        baseResponse.setErrorCode(responseStatus);

        return (T) getObject(clazz, baseResponse);
    }

}
