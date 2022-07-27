package com.example.admin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Job {

    private String jobId;

    private String jobTitle;

    private Integer minSalary;

    private Integer maxSalary;

}
