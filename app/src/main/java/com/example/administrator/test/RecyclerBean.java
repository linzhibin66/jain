package com.example.administrator.test;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class RecyclerBean implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RecyclerBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
