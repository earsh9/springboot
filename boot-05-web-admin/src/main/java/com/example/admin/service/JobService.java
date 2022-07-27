package com.example.admin.service;

import com.example.admin.bean.Job;
import com.example.admin.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    JobMapper jobMapper;

    public Job getJobById(String id){
        return jobMapper.getJobById(id);
    }
}
