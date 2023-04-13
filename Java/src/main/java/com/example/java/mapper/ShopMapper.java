package com.example.java.mapper;

import com.example.java.entity.*;
import com.example.java.entity.dto.User_goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopMapper {

    //查询商品
    List<User_goods> queryAll(Page page, User_goods ug, String serch_info, boolean desc);

    //添加商品
    @Insert("insert into goods(gname,price,info,cid,gimg,purchases,score,bid) values(#{goods.gname},#{goods.price},#{goods.info},#{goods.cid},#{goods.gimg},#{goods.purchases},#{goods.score},#{goods.bid})")
    int addGoods(Goods goods, User user);

    //查询用户是否是商家
    @Select("select * from business where uid=#{uid}")
    Business queryBusiness(User user);

    //查询商品分类名称
    @Select("select * from classify")
    List<Classify> queryClassify();

    //查询商品具体分类
    @Select("select classify_two.c2id,classify_two.cname,classify_two.cid from classify_two inner join classify on classify_two.cid = classify.cid WHERE classify_two.cid=#{cid}")
    List<Classify_two> queryClassifyDetail(Classify_two ct);
}
