package com.example.java.mapper;

import com.example.java.entity.User;
import com.example.java.entity.dto.UserDto;
import com.example.java.entity.page;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    List<UserDto> findUser(String query_content, page Page);

    @Insert("insert into user(username,password,nickname,img,qq,wechat,phone,email,role,del) " +
            "values(#{username},#{password},#{nickname},#{img},#{qq},#{wechat},#{phone},#{email},#{role},#{del})")
    int AddUser(User user);

    int Update(User user);

    @Delete("delete from user where uid=#{uid}")
    int DelUser(int uid);

    @Select("select * from user where username =#{username} and password=#{password}")
    User login(User user);

    @Update("update user set password=#{password} where username=#{username}")
    int findPassword(User user);
}



