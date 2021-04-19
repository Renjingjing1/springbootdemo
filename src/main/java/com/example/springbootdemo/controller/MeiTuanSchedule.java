package com.example.springbootdemo.controller;

import com.sankuai.meituan.shangou.open.sdk.domain.SystemParam;
import com.sankuai.meituan.shangou.open.sdk.exception.SgOpenException;
import com.sankuai.meituan.shangou.open.sdk.request.PoiMGetRequest;
import com.sankuai.meituan.shangou.open.sdk.request.RetailListRequest;
import com.sankuai.meituan.shangou.open.sdk.request.RetailSkuStockRequest;
import com.sankuai.meituan.shangou.open.sdk.response.SgOpenResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class MeiTuanSchedule {
    private static final Logger logger = LoggerFactory.getLogger(MeiTuanSchedule.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /*
    * 调用批量获取门店详细信息接口
    * */
    @Scheduled(cron = "${meituan.corn0}")
    public void mget(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select app_poi_code from meituan_bm");
        String allappcode="";
        for(Map map:list){
           String appcode= map.get("app_poi_code")+"";
           if(StringUtils.isNotEmpty(appcode)){
               allappcode+=appcode+",";
           }
        }
        if(allappcode!=""&& allappcode.length()>0){
            allappcode= allappcode.substring(0,allappcode.length()-1);
        }
        if("".equals(allappcode)){
            logger.info("MeiTuanSchedule appcode 为空");
            return;
        }
        String appinfosql="select appid,appsecret from MEITUAN_GSXX";
        List<Map<String, Object>> applist = jdbcTemplate.queryForList(appinfosql);
        for(Map map:applist){
            if(map.get("appid")!=null && map.get("appsecret") !=null){
                String appid=map.get("appid").toString();
                String appsecret =map.get("appsecret").toString();
                SystemParam systemParam = new SystemParam(appid,appsecret);
                //组建请求参数
                PoiMGetRequest poiMGetRequest = new PoiMGetRequest(systemParam);
                // 上传单门店
                poiMGetRequest.setApp_poi_codes(allappcode);
                // 上传多门店
                //poiMGetRequest.setApp_poi_codes("666,777,888");
                //发起请求
                SgOpenResponse sgOpenResponse;
                try {
                    sgOpenResponse = poiMGetRequest.doRequest();
                } catch (SgOpenException e) {
                    e.printStackTrace();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                //发起请求时的sig，用来联系美团员工排查问题时使用
                String requestSig = sgOpenResponse.getRequestSig();
                logger.info("meituan----sig======"+requestSig);
                //请求返回的结果，按照官网的接口文档自行解析即可
                String requestResult = sgOpenResponse.getRequestResult();
                logger.info("返回结果"+requestResult);

                JSONObject jsonObject = JSONObject.fromObject(requestResult);
                String data = jsonObject.getString("data");
                JSONArray array = JSONArray.fromObject(data);
                for (int i =0;i<array.size();i++){
                    String tempjson =array.get(i).toString();
                    JSONObject tempobj = JSONObject.fromObject(tempjson);
                    String apppoicode= tempobj.getString("app_poi_code");
                    String name=  tempobj.getString("name");
                    String open_level= tempobj.getString("open_level");
                    String is_online=  tempobj.getString("is_online");
                    String ctime=  tempobj.getString("ctime");
                    String utime=  tempobj.getString("utime");
                    String pre_book= tempobj.getString("pre_book");
                    String time_select= tempobj.getString("time_select");
                    String sql="update meituan_bm set name='"+name+"',open_level='"+open_level+"',is_online='"+is_online+
                            "',ctime='"+ctime+"',utime='"+utime+"',pre_book='"+pre_book+"',time_select='"+
                            time_select+"' where app_poi_code='"+apppoicode+"'";
                    logger.info("PoiMGetRequest----sql----"+sql);
                    jdbcTemplate.update(sql);
                }
            }
        }
    }
    /*
    * 调用查询门店商品列表接口
    * */
    @Scheduled(cron = "${meituan.corn1}")
    public void retailList () {
        String appinfosql="select appid,appsecret from MEITUAN_GSXX";
        List<Map<String, Object>> applist = jdbcTemplate.queryForList(appinfosql);
        for(Map appmap:applist) {
            if (appmap.get("appid") != null && appmap.get("appsecret") != null) {
                String appid = appmap.get("appid").toString();
                String appsecret = appmap.get("appsecret").toString();
                SystemParam systemParam = new SystemParam(appid, appsecret);

                RetailListRequest retailListRequest = new RetailListRequest(systemParam);
                //遍历所有的门店id，调用查询门店商品列表接口查询
                List<Map<String, Object>> list = jdbcTemplate.queryForList("select app_poi_code from meituan_bm");
                for(Map map:list){
                    Object obj = map.get("app_poi_code");
                    if(obj !=null){
                        String app_poi_code =obj.toString();
                        retailListRequest.setApp_poi_code(app_poi_code);
                        retailListRequest.setOffset(0);
                        retailListRequest.setLimit(1);
                        SgOpenResponse sgOpenResponse;
                        try {
                            sgOpenResponse = retailListRequest.doRequest();
                        } catch (SgOpenException e) {
                            e.printStackTrace();
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                        //发起请求时的sig，用来联系美团员工排查问题时使用
                        String requestSig = sgOpenResponse.getRequestSig();
                        logger.info("retailList---requestSig1------"+requestSig);
                        //请求返回的结果，按照官网的接口文档自行解析即可
                        String requestResult = sgOpenResponse.getRequestResult();
                        //第一次调用接口先查询出对应门店商品列表的总条数
                        JSONObject jsonObject = JSONObject.fromObject(requestResult);
                        String extrajson = jsonObject.getString("extra_info");
                        String num=JSONObject.fromObject(extrajson).getString("total_count");

                        //总数
                        int count=Integer.parseInt(num);
                        //每页数据条数
                        int pageSize=20;
                        //要查询的总次数
                        int times = (int) Math.ceil(count / (pageSize * 1.0));
                        logger.info("retailList-----times-----"+times);
                        for (int i = 0; i < times; i++) {
                            retailListRequest.setApp_poi_code(app_poi_code);
                            retailListRequest.setOffset(i);
                            retailListRequest.setLimit(20);
                            SgOpenResponse sgResponse;
                            try {
                                sgResponse = retailListRequest.doRequest();
                            } catch (SgOpenException e) {
                                e.printStackTrace();
                                return;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }
                            //发起请求时的sig，用来联系美团员工排查问题时使用
                            String reqSig = sgResponse.getRequestSig();
                            logger.info("retailList-------reqSig2---"+reqSig);
                            //请求返回的结果，按照官网的接口文档自行解析即可
                            String result = sgResponse.getRequestResult();
                            logger.info("etailListRequest----result---"+result);
                            JSONObject resultObject = JSONObject.fromObject(result);
                            String arr = resultObject.getString("data");
                            JSONArray jsonArray = JSONArray.fromObject(arr);
                            for (int k = 0; k < jsonArray.size(); k++) {
                                JSONObject tempjson = JSONObject.fromObject(jsonArray.get(k));
                                tempjson.getString("app_poi_code");
                                String name=tempjson.getString("name");
                                String app_food_code= tempjson.getString("app_food_code");
                                String price= tempjson.getString("price");
                                String is_sold_out= tempjson.getString("is_sold_out");
                                String ctime= tempjson.getString("ctime");
                                String utime= tempjson.getString("utime");
                                String tag_id= tempjson.getString("tag_id");
                                String is_specialty=tempjson.getString("is_specialty");
                                String audit_status= tempjson.getString("audit_status");
                                String skus = tempjson.getString("skus");
                                JSONArray skuarr = JSONArray.fromObject(skus);
                                for (int j = 0; j < skuarr.size(); j++) {
                                    JSONObject sk = JSONObject.fromObject(skuarr.get(j));
                                    String sku_id= sk.getString("sku_id");
                                    String upc= sk.getString("upc");
                                    String stock= sk.getString("stock");
                                    String location_code= sk.getString("location_code");
                                    String isSellFlag= sk.getString("isSellFlag");
                                    String updatesql="update meituan_spxx set name='"+name+"',price='"+
                                            price+"',is_sold_out='"+is_sold_out+"',ctime='"+ctime+"',utime='"+
                                            utime+"',tag_id='"+tag_id+"',is_specialty='"+is_specialty+"',audit_status='"+
                                            audit_status+"',upc='"+upc+"',stock='"+stock+"',location_code='"+
                                            location_code+"',isSellFlag='"+isSellFlag+"' where sku_id='"+sku_id+
                                            "' and app_food_code='" +app_food_code+"'";
                                    logger.info("retailListRequest----updatesql----"+updatesql);
                                    jdbcTemplate.update(updatesql);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /*
     * 调用批量更新SKU库存接口
     * */
    @Scheduled(cron = "${meituan.corn2}")
    public void retailSkuStock(){
        //查询条件表中的条件，把对应的条件组装到查询语句中
        String quersql="select spsx from meituan_bm";
        List<Map<String, Object>> querlist = jdbcTemplate.queryForList(quersql);
        for (Map querymap:querlist) {
            if(querymap.get("spsx")!=null){
                String spsx=querymap.get("spsx").toString();

                String sql="select case\n" +
                        "         when S.QTY_YL <> 0 and S.SL >= S.QTY_YL THEN S.SL - S.QTY_YL\n" +
                        "         when S.QTY_YL <> 0 and S.SL < S.QTY_YL THEN 0\n" +
                        "         else S.SL end SL,\n" +
                        "       S.app_poi_code,\n" +
                        "       S.sku_id,\n" +
                        "       S.app_food_code\n" +
                        "  from (\n" +
                        "        SELECT MIN(A.SL) SL,\n" +
                        "                A.app_poi_code,\n" +
                        "                A.sku_id,\n" +
                        "                A.app_food_code,\n" +
                        "                A.QTY_YL\n" +
                        "          FROM (SELECT SUM(A.CKSP04 + A.CKSP05) SL,\n" +
                        "                        A.SPXX01,\n" +
                        "                        D.app_poi_code,\n" +
                        "                        D.sku_id,\n" +
                        "                        D.app_food_code,\n" +
                        "                        D.QTY_YL\n" +
                        "                   FROM CKSP A, MEITUAN_BM B, CK C, MEITUAN_SPXX D, meituan_gsxx E, MEITUAN_SPXX_ZHSP F\n" +
                        "                  WHERE A.CK01 = C.CK01\n" +
                        "                    AND C.CK14 = B.BM01\n" +
                        "                    AND A.SPXX01 = F.SPXX01\n" +
                        "                    AND A.CKSP04 + A.CKSP05 >= 0\n" +
                        "                    AND D.sku_id = F.sku_id\n" +
                        "                    AND B.app_poi_code = D.app_poi_code\n" +
                        "                    AND A.GSXX01 = E.GSXX01\n" +
                        "                    AND D.is_sold_out = 1\n" +
                        "                    AND SUBSTR(A.CK01, LENGTH(A.CK01) - 1, 2) IN ('01', '02')\n" +
                        "                    AND A.CKSP12 IN ("+spsx+")" +
                        "                  GROUP BY A.SPXX01,\n" +
                        "                           D.app_poi_code,\n" +
                        "                           D.sku_id,\n" +
                        "                           D.app_food_code,\n" +
                        "                           D.QTY_YL) A\n" +
                        "         where exists\n" +
                        "         (SELECT 1\n" +
                        "                  FROM MEITUAN_SPXX_ZHSP S, CKSP K, CK C, MEITUAN_BM J\n" +
                        "                 WHERE S.SPXX01 = K.SPXX01\n" +
                        "                   AND K.CK01 = C.CK01\n" +
                        "                   AND C.CK14 = J.BM01\n" +
                        "                   AND S.sku_id = A.sku_id\n" +
                        "                   and K.spxx01 <> A.spxx01\n" +
                        "                   AND J.app_poi_code = A.app_poi_code\n" +
                        "                   AND K.CKSP12 IN (0, 1, 2, 3)\n" +
                        "                   AND SUBSTR(K.CK01, LENGTH(K.CK01) - 1, 2) IN ('01', '02'))\n" +
                        "         GROUP BY A.app_poi_code, A.sku_id, A.app_food_code, A.QTY_YL\n" +
                        "        union all     \n" +
                        "        SELECT MIN(A.SL) SL,\n" +
                        "                A.app_poi_code,\n" +
                        "                A.sku_id,\n" +
                        "                A.app_food_code,\n" +
                        "                A.QTY_YL\n" +
                        "          FROM (SELECT SUM(A.CKSP04 + A.CKSP05) SL,\n" +
                        "                        A.SPXX01,\n" +
                        "                        D.app_poi_code,\n" +
                        "                        D.sku_id,\n" +
                        "                        D.app_food_code,\n" +
                        "                        D.QTY_YL\n" +
                        "                   FROM CKSP A, MEITUAN_BM B, CK C, MEITUAN_SPXX D, meituan_gsxx E, MEITUAN_SPXX_ZHSP F\n" +
                        "                  WHERE A.CK01 = C.CK01\n" +
                        "                    AND C.CK14 = B.BM01\n" +
                        "                    AND A.SPXX01 = F.SPXX01\n" +
                        "                    AND A.CKSP04 + A.CKSP05 >= 0\n" +
                        "                    AND D.sku_id = F.sku_id\n" +
                        "                    AND B.app_poi_code = D.app_poi_code\n" +
                        "                    AND A.GSXX01 = E.GSXX01\n" +
                        "                    AND D.is_sold_out = 1\n" +
                        "                    AND SUBSTR(A.CK01, LENGTH(A.CK01) - 1, 2) IN ('01', '02')\n" +
                        "                    AND A.CKSP12 IN ("+spsx+")" +
                        "                  GROUP BY A.SPXX01,\n" +
                        "                           D.app_poi_code,\n" +
                        "                           D.sku_id,\n" +
                        "                           D.app_food_code,\n" +
                        "                           D.QTY_YL) A\n" +
                        "         where sku_id in (select sku_id from MEITUAN_SPXX_ZHSP group by sku_id having count(*) = 1)\n" +
                        "         GROUP BY A.app_poi_code, A.sku_id, A.app_food_code, A.QTY_YL  \n" +
                        "        ) S";
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
                String appinfosql="select appid,appsecret from MEITUAN_GSXX";
                List<Map<String, Object>> applist = jdbcTemplate.queryForList(appinfosql);
                for(Map appmap:applist) {
                    if (appmap.get("appid") != null && appmap.get("appsecret") != null) {
                        String appid = appmap.get("appid").toString();
                        String appsecret = appmap.get("appsecret").toString();
                        SystemParam systemParam = new SystemParam(appid, appsecret);
                        for (Map map:list) {
                            String app_poi_code= map.get("app_poi_code")+"";
                            String sku_id= map.get("sku_id")+"";
                            String app_food_code= map.get("app_food_code")+"";
                            String sl= map.get("sl")+"";
                            //组建请求参数,如有其它参数请补充完整
                            RetailSkuStockRequest retailSkuStockRequest = new RetailSkuStockRequest(systemParam);
                            retailSkuStockRequest.setApp_poi_code(app_poi_code);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            ArrayList<Object> paramlist = new ArrayList<>();
                            ArrayList<Object> skulist = new ArrayList<>();
                            HashMap<String, Object> skuMap = new HashMap<>();
                            skuMap.put("sku_id",sku_id);
                            skuMap.put("stock",sl);
                            skulist.add(skuMap);
                            hashMap.put("app_food_code",app_food_code);
                            hashMap.put("skus",skulist);
                            paramlist.add(hashMap);
                            String jsonstring = JSONObject.fromObject(paramlist).toString();
                            retailSkuStockRequest.setFood_data(jsonstring);
                            SgOpenResponse sgOpenResponse;
                            try {
                                sgOpenResponse = retailSkuStockRequest.doRequest();
                            } catch (SgOpenException e) {
                                e.printStackTrace();
                                return;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }
                            //发起请求时的sig，用来联系美团员工排查问题时使用
                            String requestSig = sgOpenResponse.getRequestSig();
                            //请求返回的结果，按照官网的接口文档自行解析即可
                            String requestResult = sgOpenResponse.getRequestResult();
                            JSONObject jsonObject = JSONObject.fromObject(requestResult);
                            String data = jsonObject.getString("data");
                            if("ok".equalsIgnoreCase(data)){
                                logger.info("更新成功 :"+requestSig);
                            }else{
                                logger.info("更新失败 :"+requestSig);
                            }
                        }
                    }
                }
            }
        }
    }
}
