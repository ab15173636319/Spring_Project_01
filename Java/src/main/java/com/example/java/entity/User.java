package com.example.java.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//省略get和set方法
@NoArgsConstructor//创建无参构造函数
@AllArgsConstructor//创建有参构造函数
public class User {
    private int uid;
    private String username;
    private String password;
    private String nickname;
    private String img;
    private String qq;
    private String wechat;
    private String phone;
    private String email;
    private String time;
    private int role;
    private int del;
}
