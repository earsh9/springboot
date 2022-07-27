package com.example.admin.mapper;

import com.example.admin.bean.Job;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JobMapper {

    public Job getJobById(String id);
}
