package com.bellwe.jobsearchcrud.service;

import com.bellwe.jobsearchcrud.dto.CreateJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.JobApplicationResponseDTO;
import com.bellwe.jobsearchcrud.dto.PatchJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.UpdateJobApplicationDTO;
import com.bellwe.jobsearchcrud.entity.JobApplication;

import java.util.List;

public interface JobApplicationService {

    List<JobApplicationResponseDTO> findAll();

    JobApplicationResponseDTO findById(int id);

    JobApplicationResponseDTO save(CreateJobApplicationDTO jobApplicationDTO);

    JobApplicationResponseDTO update(int id, UpdateJobApplicationDTO jobApplicationDTO);

    JobApplicationResponseDTO patch(int id, PatchJobApplicationDTO jobApplicationDTO);

    void deleteById(int id);

}
