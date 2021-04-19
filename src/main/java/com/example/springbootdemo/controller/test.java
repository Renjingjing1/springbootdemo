package com.example.springbootdemo.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;

public class test {
    public  void mainss(String[] args) {

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(150000)
                .setConnectTimeout(150000)
                .setConnectionRequestTimeout(150000)
                .build();
        CloseableHttpClient httpClient =  HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpEntity httpEntity = null;
        String responseContent = null;

        String url="https://waimaiopen.meituan.com/api/v1/poi/mget";

        HashMap<Object, Object> hashMap = new HashMap<>();
        int time = (int)System.currentTimeMillis()/1000;

        hashMap.put("app_id","6303");
        hashMap.put("timestamp",time);
        hashMap.put("app_poi_codes","6303_2701014");
        hashMap.put("","");



        String json="";
        HttpPost httpPost=new HttpPost(url);
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        try {
            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
            httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost);
            httpEntity = response.getEntity();
            responseContent = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(responseContent);

    }

    public static void main(String[] args) {
        String json="{\"extra_info\":{\"total_count\":9},\"data\":[{\"app_poi_code\":\"6303_2701014\",\"app_food_code\":\"A001\",\"operation\":0,\"name\":\"A001\",\"description\":\"商品描述\",\"price\":55,\"min_order_count\":1,\"unit\":\"份\",\"box_num\":1,\"box_price\":0,\"category_name\":\"测试分类\",\"secondary_category_name\":null,\"is_sold_out\":0,\"picture\":\"http://p0.meituan.net/xianfu/d83f986932fdf6ddce8be02278db3184264826.png\",\"sequence\":-1,\"skus\":\"[{\\\"available_times\\\":{\\\"friday\\\":\\\"\\\",\\\"monday\\\":\\\"\\\",\\\"saturday\\\":\\\"\\\",\\\"sunday\\\":\\\"\\\",\\\"thursday\\\":\\\"\\\",\\\"tuesday\\\":\\\"\\\",\\\"wednesday\\\":\\\"\\\"},\\\"box_num\\\":\\\"1.0\\\",\\\"box_price\\\":\\\"0.0\\\",\\\"isSellFlag\\\":1,\\\"ladder_box_num\\\":\\\"\\\",\\\"ladder_box_price\\\":\\\"\\\",\\\"limit_open_sync_stock_now\\\":false,\\\"location_code\\\":\\\"\\\",\\\"min_order_count\\\":\\\"1\\\",\\\"price\\\":\\\"55.0\\\",\\\"sku_id\\\":\\\"0101\\\",\\\"spec\\\":\\\"1000W\\\",\\\"stock\\\":\\\"10\\\",\\\"unit\\\":\\\"份\\\",\\\"upc\\\":\\\"\\\",\\\"weight\\\":\\\"430\\\",\\\"weight_unit\\\":\\\"\\\"}]\",\"standard_sku\":null,\"unstandard_skus\":null,\"upc_code\":null,\"ctime\":1618211461,\"utime\":1618211461,\"special_price\":null,\"max_order_count\":0,\"tag_id\":200000033,\"zh_id\":null,\"zh_name\":\"\",\"product_name\":null,\"flavour\":null,\"origin_name\":null,\"origin_id\":null,\"isSp\":0,\"spId\":null,\"picture_contents\":\"\",\"properties\":null,\"category_code\":\"\",\"secondary_category_code\":null,\"is_specialty\":0,\"category_code_list\":null,\"category_name_list\":null,\"category_list\":\"[{\\\"category_code\\\":\\\"\\\",\\\"category_name\\\":\\\"测试分类\\\"}]\",\"video_id\":0,\"video_url_mp4\":null,\"common_attr_value\":null,\"is_show_upc_pic_contents\":1,\"limit_sale_info\":null,\"stock_config\":\"{\\\"limit_open_sync_stock\\\":false,\\\"reset\\\":false,\\\"schedule\\\":\\\"\\\",\\\"sync_count\\\":-1,\\\"sync_next_day\\\":false,\\\"type\\\":[]}\",\"upc_image\":null,\"audit_status\":0,\"is_complete\":0,\"sell_point\":null},{\"app_poi_code\":\"6303_2701014\",\"app_food_code\":\"A002\",\"operation\":0,\"name\":\"A002\",\"description\":\"商品描述\",\"price\":55,\"min_order_count\":1,\"unit\":\"份\",\"box_num\":1,\"box_price\":0,\"category_name\":\"测试分类\",\"secondary_category_name\":null,\"is_sold_out\":0,\"picture\":\"http://p0.meituan.net/xianfu/d83f986932fdf6ddce8be02278db3184264826.png\",\"sequence\":-1,\"skus\":\"[{\\\"available_times\\\":{\\\"friday\\\":\\\"\\\",\\\"monday\\\":\\\"\\\",\\\"saturday\\\":\\\"\\\",\\\"sunday\\\":\\\"\\\",\\\"thursday\\\":\\\"\\\",\\\"tuesday\\\":\\\"\\\",\\\"wednesday\\\":\\\"\\\"},\\\"box_num\\\":\\\"1.0\\\",\\\"box_price\\\":\\\"0.0\\\",\\\"isSellFlag\\\":1,\\\"ladder_box_num\\\":\\\"\\\",\\\"ladder_box_price\\\":\\\"\\\",\\\"limit_open_sync_stock_now\\\":false,\\\"location_code\\\":\\\"\\\",\\\"min_order_count\\\":\\\"1\\\",\\\"price\\\":\\\"55.0\\\",\\\"sku_id\\\":\\\"0101\\\",\\\"spec\\\":\\\"1000W\\\",\\\"stock\\\":\\\"10\\\",\\\"unit\\\":\\\"份\\\",\\\"upc\\\":\\\"\\\",\\\"weight\\\":\\\"430\\\",\\\"weight_unit\\\":\\\"\\\"}]\",\"standard_sku\":null,\"unstandard_skus\":null,\"upc_code\":null,\"ctime\":1618211461,\"utime\":1618211461,\"special_price\":null,\"max_order_count\":0,\"tag_id\":200000033,\"zh_id\":null,\"zh_name\":\"\",\"product_name\":null,\"flavour\":null,\"origin_name\":null,\"origin_id\":null,\"isSp\":0,\"spId\":null,\"picture_contents\":\"\",\"properties\":null,\"category_code\":\"\",\"secondary_category_code\":null,\"is_specialty\":0,\"category_code_list\":null,\"category_name_list\":null,\"category_list\":\"[{\\\"category_code\\\":\\\"\\\",\\\"category_name\\\":\\\"测试分类\\\"}]\",\"video_id\":0,\"video_url_mp4\":null,\"common_attr_value\":null,\"is_show_upc_pic_contents\":1,\"limit_sale_info\":null,\"stock_config\":\"{\\\"limit_open_sync_stock\\\":false,\\\"reset\\\":false,\\\"schedule\\\":\\\"\\\",\\\"sync_count\\\":-1,\\\"sync_next_day\\\":false,\\\"type\\\":[]}\",\"upc_image\":null,\"audit_status\":0,\"is_complete\":0,\"sell_point\":null},{\"app_poi_code\":\"6303_2701014\",\"app_food_code\":\"A003\",\"operation\":0,\"name\":\"A003\",\"description\":\"商品描述\",\"price\":55,\"min_order_count\":1,\"unit\":\"份\",\"box_num\":1,\"box_price\":0,\"category_name\":\"测试分类\",\"secondary_category_name\":null,\"is_sold_out\":0,\"picture\":\"http://p0.meituan.net/xianfu/d83f986932fdf6ddce8be02278db3184264826.png\",\"sequence\":-1,\"skus\":\"[{\\\"available_times\\\":{\\\"friday\\\":\\\"\\\",\\\"monday\\\":\\\"\\\",\\\"saturday\\\":\\\"\\\",\\\"sunday\\\":\\\"\\\",\\\"thursday\\\":\\\"\\\",\\\"tuesday\\\":\\\"\\\",\\\"wednesday\\\":\\\"\\\"},\\\"box_num\\\":\\\"1.0\\\",\\\"box_price\\\":\\\"0.0\\\",\\\"isSellFlag\\\":1,\\\"ladder_box_num\\\":\\\"\\\",\\\"ladder_box_price\\\":\\\"\\\",\\\"limit_open_sync_stock_now\\\":false,\\\"location_code\\\":\\\"\\\",\\\"min_order_count\\\":\\\"1\\\",\\\"price\\\":\\\"55.0\\\",\\\"sku_id\\\":\\\"0101\\\",\\\"spec\\\":\\\"1000W\\\",\\\"stock\\\":\\\"10\\\",\\\"unit\\\":\\\"份\\\",\\\"upc\\\":\\\"\\\",\\\"weight\\\":\\\"430\\\",\\\"weight_unit\\\":\\\"\\\"}]\",\"standard_sku\":null,\"unstandard_skus\":null,\"upc_code\":null,\"ctime\":1618211461,\"utime\":1618211461,\"special_price\":null,\"max_order_count\":0,\"tag_id\":200000033,\"zh_id\":null,\"zh_name\":\"\",\"product_name\":null,\"flavour\":null,\"origin_name\":null,\"origin_id\":null,\"isSp\":0,\"spId\":null,\"picture_contents\":\"\",\"properties\":null,\"category_code\":\"\",\"secondary_category_code\":null,\"is_specialty\":0,\"category_code_list\":null,\"category_name_list\":null,\"category_list\":\"[{\\\"category_code\\\":\\\"\\\",\\\"category_name\\\":\\\"测试分类\\\"}]\",\"video_id\":0,\"video_url_mp4\":null,\"common_attr_value\":null,\"is_show_upc_pic_contents\":1,\"limit_sale_info\":null,\"stock_config\":\"{\\\"limit_open_sync_stock\\\":false,\\\"reset\\\":false,\\\"schedule\\\":\\\"\\\",\\\"sync_count\\\":-1,\\\"sync_next_day\\\":false,\\\"type\\\":[]}\",\"upc_image\":null,\"audit_status\":0,\"is_complete\":0,\"sell_point\":null},{\"app_poi_code\":\"6303_2701014\",\"app_food_code\":\"A004\",\"operation\":0,\"name\":\"A004\",\"description\":\"商品描述\",\"price\":55,\"min_order_count\":1,\"unit\":\"份\",\"box_num\":1,\"box_price\":0,\"category_name\":\"测试分类\",\"secondary_category_name\":null,\"is_sold_out\":0,\"picture\":\"http://p0.meituan.net/xianfu/d83f986932fdf6ddce8be02278db3184264826.png\",\"sequence\":-1,\"skus\":\"[{\\\"available_times\\\":{\\\"friday\\\":\\\"\\\",\\\"monday\\\":\\\"\\\",\\\"saturday\\\":\\\"\\\",\\\"sunday\\\":\\\"\\\",\\\"thursday\\\":\\\"\\\",\\\"tuesday\\\":\\\"\\\",\\\"wednesday\\\":\\\"\\\"},\\\"box_num\\\":\\\"1.0\\\",\\\"box_price\\\":\\\"0.0\\\",\\\"isSellFlag\\\":1,\\\"ladder_box_num\\\":\\\"\\\",\\\"ladder_box_price\\\":\\\"\\\",\\\"limit_open_sync_stock_now\\\":false,\\\"location_code\\\":\\\"\\\",\\\"min_order_count\\\":\\\"1\\\",\\\"price\\\":\\\"55.0\\\",\\\"sku_id\\\":\\\"0101\\\",\\\"spec\\\":\\\"1000W\\\",\\\"stock\\\":\\\"10\\\",\\\"unit\\\":\\\"份\\\",\\\"upc\\\":\\\"\\\",\\\"weight\\\":\\\"430\\\",\\\"weight_unit\\\":\\\"\\\"}]\",\"standard_sku\":null,\"unstandard_skus\":null,\"upc_code\":null,\"ctime\":1618211461,\"utime\":1618211461,\"special_price\":null,\"max_order_count\":0,\"tag_id\":200000033,\"zh_id\":null,\"zh_name\":\"\",\"product_name\":null,\"flavour\":null,\"origin_name\":null,\"origin_id\":null,\"isSp\":0,\"spId\":null,\"picture_contents\":\"\",\"properties\":null,\"category_code\":\"\",\"secondary_category_code\":null,\"is_specialty\":0,\"category_code_list\":null,\"category_name_list\":null,\"category_list\":\"[{\\\"category_code\\\":\\\"\\\",\\\"category_name\\\":\\\"测试分类\\\"}]\",\"video_id\":0,\"video_url_mp4\":null,\"common_attr_value\":null,\"is_show_upc_pic_contents\":1,\"limit_sale_info\":null,\"stock_config\":\"{\\\"limit_open_sync_stock\\\":false,\\\"reset\\\":false,\\\"schedule\\\":\\\"\\\",\\\"sync_count\\\":-1,\\\"sync_next_day\\\":false,\\\"type\\\":[]}\",\"upc_image\":null,\"audit_status\":0,\"is_complete\":0,\"sell_point\":null},{\"app_poi_code\":\"6303_2701014\",\"app_food_code\":\"A005\",\"operation\":0,\"name\":\"A005\",\"description\":\"商品描述\",\"price\":55,\"min_order_count\":1,\"unit\":\"份\",\"box_num\":1,\"box_price\":0,\"category_name\":\"测试分类\",\"secondary_category_name\":null,\"is_sold_out\":0,\"picture\":\"http://p0.meituan.net/xianfu/d83f986932fdf6ddce8be02278db3184264826.png\",\"sequence\":-1,\"skus\":\"[{\\\"available_times\\\":{\\\"friday\\\":\\\"\\\",\\\"monday\\\":\\\"\\\",\\\"saturday\\\":\\\"\\\",\\\"sunday\\\":\\\"\\\",\\\"thursday\\\":\\\"\\\",\\\"tuesday\\\":\\\"\\\",\\\"wednesday\\\":\\\"\\\"},\\\"box_num\\\":\\\"1.0\\\",\\\"box_price\\\":\\\"0.0\\\",\\\"isSellFlag\\\":1,\\\"ladder_box_num\\\":\\\"\\\",\\\"ladder_box_price\\\":\\\"\\\",\\\"limit_open_sync_stock_now\\\":false,\\\"location_code\\\":\\\"\\\",\\\"min_order_count\\\":\\\"1\\\",\\\"price\\\":\\\"55.0\\\",\\\"sku_id\\\":\\\"0101\\\",\\\"spec\\\":\\\"1000W\\\",\\\"stock\\\":\\\"10\\\",\\\"unit\\\":\\\"份\\\",\\\"upc\\\":\\\"\\\",\\\"weight\\\":\\\"430\\\",\\\"weight_unit\\\":\\\"\\\"}]\",\"standard_sku\":null,\"unstandard_skus\":null,\"upc_code\":null,\"ctime\":1618211461,\"utime\":1618211461,\"special_price\":null,\"max_order_count\":0,\"tag_id\":200000033,\"zh_id\":null,\"zh_name\":\"\",\"product_name\":null,\"flavour\":null,\"origin_name\":null,\"origin_id\":null,\"isSp\":0,\"spId\":null,\"picture_contents\":\"\",\"properties\":null,\"category_code\":\"\",\"secondary_category_code\":null,\"is_specialty\":0,\"category_code_list\":null,\"category_name_list\":null,\"category_list\":\"[{\\\"category_code\\\":\\\"\\\",\\\"category_name\\\":\\\"测试分类\\\"}]\",\"video_id\":0,\"video_url_mp4\":null,\"common_attr_value\":null,\"is_show_upc_pic_contents\":1,\"limit_sale_info\":null,\"stock_config\":\"{\\\"limit_open_sync_stock\\\":false,\\\"reset\\\":false,\\\"schedule\\\":\\\"\\\",\\\"sync_count\\\":-1,\\\"sync_next_day\\\":false,\\\"type\\\":[]}\",\"upc_image\":null,\"audit_status\":0,\"is_complete\":0,\"sell_point\":null}]}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        String arr = jsonObject.getString("data");
        JSONArray jsonArray = JSONArray.fromObject(arr);
        for (int i = 0; i < jsonArray.size(); i++) {
            System.out.println("-----"+jsonArray.get(i));
            JSONObject tempjson = JSONObject.fromObject(jsonArray.get(i));
           /* System.out.println(tempjson.getString("app_poi_code"));
            System.out.println(tempjson.getString("name"));
            System.out.println(tempjson.getString("app_food_code"));
            System.out.println(tempjson.getString("price"));
            System.out.println(tempjson.getString("is_sold_out"));
            System.out.println(tempjson.getString("ctime"));
            System.out.println(tempjson.getString("utime"));
            System.out.println(tempjson.getString("tag_id"));
            System.out.println(tempjson.getString("is_specialty"));
            System.out.println(tempjson.getString("audit_status"));*/
            /*String skus = tempjson.getString("skus");
            JSONArray skuarr = JSONArray.fromObject(skus);
            for (int j = 0; j < skuarr.size(); j++) {
                System.out.println(skuarr.get(j));
                JSONObject sk = JSONObject.fromObject(skuarr.get(j));
                System.out.println(sk.getString("sku_id"));
                System.out.println(sk.getString("upc"));
                System.out.println(sk.getString("stock"));
                System.out.println(sk.getString("location_code"));
                System.out.println(sk.getString("isSellFlag"));
            }*/

        }
        int count=21;
        int pageSize=20;
        int times = (int) Math.ceil(count / (pageSize * 1.0));

        System.out.println(times);

    }
}
