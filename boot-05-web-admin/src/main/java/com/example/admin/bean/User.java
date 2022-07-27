package com.example.admin.bean;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    //标识当前的两个属性在数据库中不存在
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String password;

    //以下为数据库里的字段
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
