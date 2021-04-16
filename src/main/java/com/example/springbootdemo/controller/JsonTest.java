package com.example.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSON;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonTest {
    public static void main(String[] args) {
      String json=  "[{\"app_food_code\":\"food_0001\",\"skus\":[{\"sku_id\":\"5\",\"stock\":\"10\"},{...}]},{...}]";
        HashMap<String, Object> hashMap = new HashMap<>();
        ArrayList<Object> list = new ArrayList<>();
        ArrayList<Object> skulist = new ArrayList<>();
        HashMap<String, Object> skuMap = new HashMap<>();
        skuMap.put("sku_id","5");
        skuMap.put("stock","10");
        skulist.add(skuMap);
        hashMap.put("app_food_code","001");
        hashMap.put("skus",skulist);
        list.add(hashMap);
        String jsonString = JSONObject.toJSONString(list);
        System.out.println(jsonString);

    }
}
