package com.example.java.entity;

import com.example.java.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class page {
    private int pageNumber = 1;
    private int pageSize = 5;
    private int pageCount;
    private int total;

//    private int start = (pageNumber-1)*pageSize;

    private int getStart() {
        return (pageNumber - 1) * pageSize;
    }

    public int getPages() {
        return total % pageSize > 0 ? total / pageSize + 1 : total / pageSize;
    }

    //总页数
    public int calculation() {
        return total % pageSize > 0 ? total / pageSize + 1 : total / pageSize;
    }
}
