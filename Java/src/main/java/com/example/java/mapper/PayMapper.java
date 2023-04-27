package com.example.java.mapper;

import com.example.java.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PayMapper {

    @Select("select * from user where uid=#{uid}")
    User queryUser(User user);

    //支付
    @Update("update user set balance=#{balance} where uid=#{uid}")
    int toPayInt(double balance, int uid);
}
