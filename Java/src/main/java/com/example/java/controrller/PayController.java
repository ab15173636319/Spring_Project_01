package com.example.java.controrller;


import com.example.java.entity.User;
import com.example.java.mapper.PayMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayMapper payMapper;

    public boolean pay(Double price, User user) {
        Map<String, Object> map = new HashMap<>();
        user = payMapper.queryUser(user);
        int backNum = payMapper.toPayInt(user.getBalance() - price, user.getUid());
        return backNum > 0;
    }


}
