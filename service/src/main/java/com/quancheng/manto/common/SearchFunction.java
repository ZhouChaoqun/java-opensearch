package com.quancheng.manto.common;

/**
 * @author zz 2019/1/3
 */
public class SearchFunction {

    //搜索函数

    public static String IN_POLYGON = "in_polygon(polygon,{0},{1})>0";

    public static String DISTANCE_FILTER = "distance(longitude,latitude,\"{0}\",\"{1}\")<={2}";

    public static String DISTANCE_SORT = "distance(longitude,latitude,\"{0}\",\"{1}\")";



    //外卖配送范围搜索相关索引
    public static final String HAVE_POLYGON_INDEX = "have_polygon";

    public static final String SHIPPING_DIS_INDEX = "shipping_dis";
}
