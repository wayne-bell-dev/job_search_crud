package com.bellwe.jobsearchcrud.service;

import com.bellwe.jobsearchcrud.dao.JobApplicationRepository;
import com.bellwe.jobsearchcrud.entity.JobApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @Override
    public List<JobApplication> findAll() {
        return jobApplicationRepository.findAll();
    }

    @Override
    public JobApplication findById(int id) {
        JobApplication theApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Did not find job application by id - " + id));

        return theApplication;
    }

    @Override
    public JobApplication save(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    @Override
    public JobApplication update(int id, JobApplication jobApplication) {
        JobApplication updateApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Did not find the job application by id - " + id));

        updateApplication.setJobTitle(jobApplication.getJobTitle());
        updateApplication.setCompany(jobApplication.getCompany());
        updateApplication.setDateApplied(jobApplication.getDateApplied());
        updateApplication.setNotes(jobApplication.getNotes());
        updateApplication.setStatus(jobApplication.getStatus());

        return jobApplicationRepository.save(updateApplication);
    }

    @Override
    public void deleteById(int id) {
        jobApplicationRepository.deleteById(id);
    }
}
