package com.example.java.mapper;

import com.example.java.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface PageMapper {
    @Select("select count(*) from user")
    int GetPage();



};




