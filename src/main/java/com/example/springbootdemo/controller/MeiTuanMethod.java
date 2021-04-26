package com.example.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeiTuanMethod {
    @Autowired
    MeiTuanSchedule meiTuanSchedule;
    @RequestMapping("/mget")
    public String mget(){
        meiTuanSchedule.mget();
        //return "ok";
       return "执行批量获取门店详细信息接口成功";
    }
    @RequestMapping("/retailList")
    public String retailList(){
        meiTuanSchedule.retailList();
        return "执行查询门店商品列表接口成功";
    }
    @RequestMapping("/retailSkuStock")
    public String retailSkuStock(){
        meiTuanSchedule.retailSkuStock();
        return "执行批量更新sku库存接口成功";
    }
}
