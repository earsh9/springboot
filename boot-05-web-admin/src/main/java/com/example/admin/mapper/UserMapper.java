package com.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.bean.User;
import org.apache.ibatis.annotations.Mapper;

/*
* MyBatisPlus优点之一：只需要我们的Mapper继承MyBatisPlus的BaseMapper 就可以拥有CRUD能力，减轻开发工作。
* */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
