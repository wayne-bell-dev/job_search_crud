package com.bellwe.jobsearchcrud.service;

import com.bellwe.jobsearchcrud.dao.JobApplicationRepository;
import com.bellwe.jobsearchcrud.dto.CreateJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.JobApplicationResponseDTO;
import com.bellwe.jobsearchcrud.dto.PatchJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.UpdateJobApplicationDTO;
import com.bellwe.jobsearchcrud.entity.JobApplication;
import com.bellwe.jobsearchcrud.mapper.JobApplicationMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, JobApplicationMapper jobApplicationMapper) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobApplicationMapper = jobApplicationMapper;
    }

    @Override
    public List<JobApplicationResponseDTO> findAll() {
        List<JobApplication> jobApplications = jobApplicationRepository.findAll();
        return jobApplicationMapper.toResponseDtoList(jobApplications);
    }

    @Override
    public JobApplicationResponseDTO findById(int id) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find job application by id - " + id));

        return jobApplicationMapper.toResponseDto(jobApplication);
    }

    @Override
    public JobApplicationResponseDTO save(CreateJobApplicationDTO jobApplicationDTO) {
        JobApplication jobApplication = jobApplicationMapper.toJobApplication(jobApplicationDTO);
        JobApplication savedJob = jobApplicationRepository.save(jobApplication);
        return jobApplicationMapper.toResponseDto(savedJob);
    }

    @Override
    public JobApplicationResponseDTO update(int id, UpdateJobApplicationDTO jobApplicationDTO) {
        JobApplication updateApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the job application by id - " + id));

        jobApplicationMapper.updateJobApplication(updateApplication, jobApplicationDTO);

        JobApplication updatedJob = jobApplicationRepository.save(updateApplication);

        return jobApplicationMapper.toResponseDto(updatedJob);
    }

    @Override
    public JobApplicationResponseDTO patch(int id, PatchJobApplicationDTO jobApplicationDTO) {

        jobApplicationDTO.validate();

        JobApplication patchApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the job application by id - " + id));

        jobApplicationMapper.patchJobApplication(patchApplication, jobApplicationDTO);

        JobApplication patchedJob = jobApplicationRepository.save(patchApplication);

        return jobApplicationMapper.toResponseDto(patchedJob);
    }

    @Override
    public void deleteById(int id) {
        jobApplicationRepository.deleteById(id);
    }
}
