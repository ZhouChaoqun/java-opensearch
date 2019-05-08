package com.quancheng.manto.utils;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zz 2018/11/13
 */
@Component
public class OpenSearchUtil {

    private static String accesskey;

    private static String secret;

    private static String host;

    private static String resAppName;

    public static SearcherClient getSearchClient() {

        OpenSearch openSearch = new OpenSearch(accesskey, secret, host);
        OpenSearchClient serviceClient = new OpenSearchClient(openSearch);

        return new SearcherClient(serviceClient);
    }

    public static String getResAppName() {
        return resAppName;
    }

    @Value("${openSearch.accesskey}")
    public void setAccesskey(String accesskey) {
        OpenSearchUtil.accesskey = accesskey;
    }

    @Value("${openSearch.secret}")
    public void setSecret(String secret) {
        OpenSearchUtil.secret = secret;
    }

    @Value("${openSearch.host}")
    public void setHost(String host) {
        OpenSearchUtil.host = host;
    }

    @Value("${restaurant.appName}")
    public void setResAppName(String resAppName) {
        OpenSearchUtil.resAppName = resAppName;
    }
}
