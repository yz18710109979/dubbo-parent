package com.boot.demo.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class User extends Model<User>{
    private Long id;
    private String name;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
