package com.example.java.mapper;

import com.example.java.entity.*;
import com.example.java.entity.dto.Goods_remark;
import com.example.java.entity.dto.User_goods;
import com.example.java.entity.dto.goodDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopMapper {

    //查询商品
    List<User_goods> queryAll(Page page, User_goods ug, String serch_info, boolean desc);

    //添加商品
    @Insert("insert into goods(gname,price,info,c2id,gimg,purchases,score,bid) " +
            "values(#{goods.gname},#{goods.price},#{goods.info},#{goods.c2id},#{goods.gimg},#{goods.purchases},#{goods.score},#{goods.bid})")
    int addGoods(Goods goods, User user);

    //查询用户是否是商家
    @Select("select * from business where uid=#{uid}")
    Business queryBusiness(User user);

    //查询商品是否是该商家的
    @Select("select * from goods where gid=#{gid} and bid=#{uid}")
    Goods trueGoods(int gid, int uid);

    //删除商品
    @Delete("delete from user where gid=#{gid} and bid=#{bid}")
    int delGood(Goods goods);

    //查询商品分类名称
    @Select("select * from classify")
    List<Classify> queryClassify();

    //查询商品具体分类
    @Select("select classify_two.c2id,classify_two.cname,classify_two.cid " +
            "from classify_two inner join classify on classify_two.cid = classify.cid " +
            "WHERE classify_two.cid=#{cid}")
    List<Classify_two> queryClassifyDetail(Classify c);

    //查询商品详情
    //商品部分
    @Select("SELECT user.uid,nickname,img,gid,gname,price,info,gimg,purchases,score from user INNER JOIN business on user.uid = business.uid INNER JOIN goods on business.bid = goods.bid where goods.gid=#{gid}")
    goodDetail queryGoodDetail(User_goods ug);

    //    查询评论
    List<Remarks> queryRemark(Page page, User_goods ug);

    //查询评论用户
    @Select("SELECT rid,remark,remarks.time,pariseCount,remarkCount,username,nickname,img " +
            "from remarks INNER JOIN user on user.uid = remarks.uid WHERE remarks.gid=#{gid} limit #{page.start},#{page.pageSize}")
    List<Goods_remark> queryRemarkUser(int gid, Page page);

    @Update("update goods set score=#{score}")
    int appraise(int score);

    @Update("update remark set score=#{score} where uid=#{uid}")
    int userAppraise(int score, int uid);

    @Insert("insert into goods(gname,price,info,c2id,gimg,purchases,score,bid,title_img) " +
            "values(#{goods.gname},#{goods.price},#{goods.info},1,#{goods.gimg},#{goods.purchases},#{goods.score},#{goods.bid},#{title_img})")
    int recommendations(Goods goods);

    @Select("select * from titleimg")
    List<TitleImg> titleImg();
}
