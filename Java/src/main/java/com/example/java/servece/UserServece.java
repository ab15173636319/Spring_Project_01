package com.example.java.servece;

import com.example.java.entity.User;
import com.example.java.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServece {

    @Autowired
    private UserMapper userMapper;


}
