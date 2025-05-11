package com.bellwe.jobsearchcrud.service;

import com.bellwe.jobsearchcrud.entity.JobApplication;

import java.util.List;

public interface JobApplicationService {

    List<JobApplication> findAll();

    JobApplication findById(int id);

    JobApplication save(JobApplication jobApplication);

    JobApplication update(int id, JobApplication jobApplication);

    void deleteById(int id);

}
