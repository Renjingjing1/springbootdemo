package com.example.springbootdemo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class test {
    @Autowired
    Environment env;
    //@Test
    public  void fun() {

        String json="{\"data\":[{\"app_id\":6303,\"app_poi_code\":\"6303_2701014\",\"name\":\"t_n9SLwvyjmV\",\"address\":\"南极洲04号站\",\"latitude\":29772885,\"longitude\":95402285,\"pic_url\":\"http://p1.meituan.net/160.120.90/crm/__37375183__1582979.png\",\"phone\":\"010-00000000\",\"standby_tel\":\"010-00000000\",\"shipping_fee\":2,\"shipping_time\":\"00:00-23:59\",\"promotion_info\":\"此门店为线上虚拟测试门店，不支持真实配送，请勿下单，感谢您的配合。如有用户因误操作等情况进行了下单，请用户自行及时取消订单；门店及平台支持取消订单，且不作任何额外补偿，请用户知悉。\",\"remark\":\"\",\"open_level\":1,\"is_online\":1,\"invoice_support\":1,\"invoice_min_price\":0,\"invoice_description\":\"\",\"city_id\":542600,\"location_id\":0,\"ctime\":1497584417,\"utime\":1618255000,\"third_tag_name\":null,\"tag_name\":\"综合日用百货店\",\"settlement_poi_id\":null,\"app_brand_code\":null,\"pre_book\":0,\"time_select\":1,\"pre_book_min_days\":0,\"pre_book_max_days\":0,\"pic_url_large\":null,\"mt_type_id\":null,\"logistics_codes\":\"0000\"}]}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        String data = jsonObject.getString("data");
        System.out.println("配置文件："+env.getProperty("baseSales.corn0"));
        JSONArray array = JSONArray.fromObject(data);
        for (int i =0;i<array.size();i++){
            String tempjson =array.get(i).toString();
            JSONObject tempobj = JSONObject.fromObject(tempjson);
            System.out.println(tempobj.getString("name"));
            System.out.println(tempobj.getString("address"));
            System.out.println(tempobj.getString("promotion_info"));
            System.out.println(tempobj.getString("city_id"));
            System.out.println(tempobj.getString("tag_name"));
            System.out.println(tempobj.getString("settlement_poi_id"));
        }
    }


    @Test
    public void test(){
        ArrayList<String> list = new ArrayList<>();
         list.add("123");
         list.add("234");
         list.add("345");
         String str="";
         for(int i=0;i<list.size();i++){
             str+=list.get(i)+",";
         }
        System.out.println(str.substring(0,str.length()-1));
    }
}
