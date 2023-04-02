package com.example.java.controrller;

import com.example.java.entity.User;
import com.example.java.entity.dto.UserDto;
import com.example.java.entity.page;
import com.example.java.mapper.PageMapper;
import com.example.java.mapper.UserMapper;
import com.example.java.util.JwtUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends PageController {
    @Autowired
    private UserMapper userMapper;
    private PageMapper pageMapper;

    //增加用户
    @PostMapping("/reg")
    //RequestBody：前端提交一个JSON对象来时，可将这个对象转换为普通对象
    public Integer save(@RequestBody User user) {
        return userMapper.AddUser(user);
    }

    //修改用户信息
    @PostMapping("/modify")
    public Integer Modify(@RequestBody User user) {
        return userMapper.Update(user);
    }

    //获取所有用户信息
    @PostMapping("/queryAll")
    public Map<String, Object> index(String query_content, page Page) {
        Map<String, Object> map = new HashMap<>();
        //获取所有用户信息
        List<UserDto> list = userMapper.findUser(query_content, Page);
        System.out.println(list);
        //数据总条数
        Page.setTotal(list.size());
        //分页总数
        Page.setPageCount(Page.calculation());
        //用户信息
        map.put("user", list);
        //分页信息
        map.put("page", Page);
        return map;
    }

    //删除用户
    @PostMapping("/delUser")
    public int DelUser(@RequestBody User user) {
        if (user.getDel() == 0) {
            return 0;
        } else {
            return userMapper.DelUser(user.getUid());
        }
    }

    //用户登录
    @PostMapping("/login")
    public Map<String, Object> login(User user) {
        Map<String, Object> map = new HashMap<>();
        user = userMapper.login(user);
        if (user != null) {
            //生成token
            Map<String, String> tokenmap = new HashMap<>();
            tokenmap.put("username", user.getUsername());
            tokenmap.put("password", user.getPassword());
            String token = JwtUtil.getToken(tokenmap);
            map.put("token", token);
            map.put("message", "登录成功");
            map.put("success", true);
            map.put("user", user);
        } else {
            map.put("success", false);
            map.put("message", "账号或密码错误");
        }
        return map;
    }

    //修改密码
    @PostMapping("/findPassword")
    public Map<String, Object> findPass(User user) {
        int backNum = userMapper.findPassword(user);
        Map<String, Object> map = new HashMap<>();
        System.out.println(user);
        if (backNum > 0) {
            map.put("success", true);
            map.put("message", "修改成功");
        } else {
            map.put("success", false);
            map.put("message", "修改失败");
        }
        return map;
    }
}
