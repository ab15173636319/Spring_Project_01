package com.example.java.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    private int pageNumber = 1;
    private int pageSize = 5;
    private int pageCount;
    private int total;

    public int getStart() {
        return (pageNumber - 1) * pageSize;
    }

    //总页数
    public int calculation() {
        return total % pageSize > 0 ? total / pageSize + 1 : total / pageSize;
    }
}
