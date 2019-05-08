package com.quancheng.manto.biz;

import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONObject;
import com.aliyun.opensearch.sdk.generated.search.SearchParams;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.aliyun.opensearch.search.SearchResultDebug;
import com.quancheng.manto.common.SearchResultNodeName;
import com.quancheng.manto.utils.OpenSearchUtil;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;

/**
 * @author zz 2018/12/19
 */

@Component
public class SearchBiz {

    private static final QcLog LOGGER = LogUtil.getLogger(SearchBiz.class);

    private static final String URL_DECODE_FORMAT = "utf-8";

    public JSONObject search(SearchParams searchParams) {

        if (searchParams == null) {
            return null;
        }
        SearcherClient searcher = OpenSearchUtil.getSearchClient();
        if (searcher == null) {
            return null;
        }

        JSONObject result;
        try {
            SearchResult searchResult = searcher.execute(searchParams);
            JSONObject resultJson = new JSONObject(searchResult.getResult());

            String status = resultJson.getString(SearchResultNodeName.STATUS);
            if (!SearchResultNodeName.SEARCH_SUCCESS_STATUS.equals(status)) {
                return null;
            }

            result = (JSONObject) resultJson.get(SearchResultNodeName.RESULT);

            //打印搜索请求信息
            SearchResultDebug searchdebugrst = searcher.executeDebug(searchParams);
            String url = URLDecoder.decode(searchdebugrst.getRequestUrl().toString(), URL_DECODE_FORMAT);
            LOGGER.warn("搜索请求url:{}", url);
        } catch (Exception e) {
            LOGGER.error("调用open search查询异常", e);
            return null;
        }

        return result;
    }
}
