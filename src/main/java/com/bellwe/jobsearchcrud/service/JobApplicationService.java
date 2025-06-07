package com.bellwe.jobsearchcrud.service;

import com.bellwe.jobsearchcrud.dto.CreateJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.JobApplicationResponseDTO;
import com.bellwe.jobsearchcrud.dto.PatchJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.UpdateJobApplicationDTO;
import com.bellwe.jobsearchcrud.entity.JobApplication;

import java.util.List;

/**
 * Service interface for managing job applications.
 * Provides methods for CRUD operations and partial updates.
 */
public interface JobApplicationService {

    /**
     * Retrieves all job applications.
     *
     * @return a list of job application response DTOs
     */
    List<JobApplicationResponseDTO> findAll();

    /**
     * Retrieves a job application by its ID.
     *
     * @param id the ID of the job application
     * @return the job application response DTO
     * @throws jakarta.persistence.EntityNotFoundException if the application is not found
     */
    JobApplicationResponseDTO findById(int id);

    /**
     * Creates a new job application.
     *
     * @param jobApplicationDTO the DTO containing job application data to create
     * @return the created job application response DTO
     */
    JobApplicationResponseDTO save(CreateJobApplicationDTO jobApplicationDTO);

    /**
     * Updates an existing job application by replacing all fields.
     *
     * @param id  the ID of the job application to update
     * @param jobApplicationDTO the DTO containing new job application data
     * @return the updated job application response DTO
     * @throws jakarta.persistence.EntityNotFoundException if the application is not found
     */
    JobApplicationResponseDTO update(int id, UpdateJobApplicationDTO jobApplicationDTO);

/**
 * Partially updates a job application with only non-null fields from the patch DTO.
 *
 * @param id  the ID of the job application to patch
 * @param jobApplicationDTO the DTO containing partial data to update
 * @return the patched job application response DTO
 * @throws jakarta.persistence.EntityNotFoundException if the application is not found
 * @throws IllegalArgumentException if patch validation fails
 */
    JobApplicationResponseDTO patch(int id, PatchJobApplicationDTO jobApplicationDTO);

    /**
     * Deletes a job application by its ID.
     *
     * @param id the ID of the job application to delete
     */
    void deleteById(int id);

}
