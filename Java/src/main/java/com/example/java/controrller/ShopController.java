package com.example.java.controrller;

import com.example.java.entity.*;
import com.example.java.entity.dto.Goods_remark;
import com.example.java.entity.dto.User_goods;
import com.example.java.mapper.PubMapper;
import com.example.java.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.java.entity.dto.goodDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class ShopController {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private PubMapper pubMapper;

    //查询所有商品，包括分页。模糊查询，id查询，降序排列
    @PostMapping("/queryAll")
    public Map<String, Object> queryAll(Page page, User_goods ug, String serch_info, String desc) {
        Map<String, Object> map = new HashMap<>();
        boolean isDesc = desc != null && desc.equals("yes");
        List<User_goods> list = shopMapper.queryAll(page, ug, serch_info, isDesc);
        System.out.println(list);
        page.setTotal(pubMapper.getTotal("goods"));//获取内容总条数
        page.setPageCount(page.calculation());//获取总页数
        if (list != null) {
            map.put("success", true);
            map.put("mes", "查询成功");
            map.put("goods", list);
            map.put("page", page);
        } else {
            map.put("success", false);
            map.put("mes", "查询失败");
        }
        return map;
    }

    //获取分类总类
    @PostMapping("/queryClassify")
    public Map<String, Object> queryClassify() {
        Map<String, Object> map = new HashMap<>();
        List<Classify> cla = shopMapper.queryClassify();
        map.put("classify", cla);
        return map;
    }

    //查询分类详情
    @PostMapping("/queryClassifyDeatil")
    public Map<String, Object> queryClassifyDetail(Classify ct) {
        System.out.println(ct);
        Map<String, Object> map = new HashMap<>();
        List<Classify_two> list = shopMapper.queryClassifyDetail(ct);
        map.put("list", list);
        return map;
    }

    //分类树型结构
    @PostMapping("/classifyTree")
    public Map<Object, Object> classifyTree() {
        Map<Object, Object> map = new HashMap<>();
        List<Classify> classifyList = shopMapper.queryClassify();
        for (Classify classify : classifyList
        ) {
            classify.setCt(shopMapper.queryClassifyDetail(classify));
//            List<Classify_two> classifyTwoList = shopMapper.queryClassifyDetail(classify);
        }
        map.put("list", classifyList);
        return map;
    }


    //添加商品
    @PostMapping("/addGoods")
    public Map<String, Object> addGoods(Goods goods, User user) {
        Map<String, Object> map = new HashMap<>();
        Business bus = shopMapper.queryBusiness(user);
        //设置不允许商家自行修改的参数
        goods.setBid(bus.getBid());
        goods.setPurchases(0);
        goods.setScore(10);
        System.out.println(goods);
        if (bus != null) {
            int backNum = shopMapper.addGoods(goods, user);
            if (backNum > 0) {
                map.put("success", true);
                map.put("message", "添加成功");
            } else {
                map.put("success", false);
                map.put("message", "添加失败");
            }
        } else {
            map.put("success", false);
            map.put("message", "你不是商家");
        }
        return map;
    }

    //查询商品详情
    @PostMapping("/queryDetail")
    public Map<String, Object> queryDetail(User_goods ug, Page page) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> objectMap = new HashMap<>();
        goodDetail gd = shopMapper.queryGoodDetail(ug);//商品详情
        List<Goods_remark> list = shopMapper.queryRemarkUser(ug.getGid(), page);
        page.setTotal(list.size());
        objectMap.put("remark", list);
        objectMap.put("page", page);
        map.put("goods", gd);
        map.put("map", objectMap);
        return map;
    }

    //评价商品
    @PostMapping("/appraise")
    public boolean appraise(int score, User user) {
        Boolean b;
        int backNum = shopMapper.appraise(score) + shopMapper.userAppraise(score, user.getUid());
        b = backNum > 1;
        return b;
    }

    //商家删除自家商品
    @PostMapping("/delGoods")
    public Boolean delGoods(Goods goods) {
        return shopMapper.delGood(goods) > 0;
    }

    //将商品添加到热门推荐
    @PostMapping("/Recommendations")
    public Boolean recommendations(Goods goods) {
        return shopMapper.recommendations(goods) > 0;
    }

    //获取首页轮播图片
    @PostMapping("/titleimg")
    public List<TitleImg> titleImg() {
        return shopMapper.titleImg();
    }
}
