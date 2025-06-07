package com.bellwe.jobsearchcrud.controller;

import com.bellwe.jobsearchcrud.dto.CreateJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.JobApplicationResponseDTO;
import com.bellwe.jobsearchcrud.dto.PatchJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.UpdateJobApplicationDTO;
import com.bellwe.jobsearchcrud.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JobApplicationRestController {

    private final JobApplicationService jobApplicationService;

    @Autowired
    public JobApplicationRestController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    /**
     * Retrieves all job applications.
     *
     * @return a list of all job applications
     */
    @GetMapping("/jobs")
    public List<JobApplicationResponseDTO> findAllApplications() {

        return jobApplicationService.findAll();

    }

    /**
     * Retrieves a job application by its ID.
     *
     * @param applicationId the ID of the job application to retrieve
     * @return the job application with the specified ID
     * @throws jakarta.persistence.EntityNotFoundException if no job application is found with the given ID
     */
    @GetMapping("/jobs/{applicationId}")
    public JobApplicationResponseDTO findApplication(@PathVariable int applicationId) {

        return jobApplicationService.findById(applicationId);

    }

    /**
     * Creates a new job application.
     *
     * @param createDto the job application details to be created
     * @return the created job application
     */
    @PostMapping("/jobs")
    public JobApplicationResponseDTO saveApplication(@Valid @RequestBody CreateJobApplicationDTO createDto) {

        return jobApplicationService.save(createDto);

    }

    /**
     * Updates an existing job application by replacing all fields.
     *
     * @param applicationId  the ID of the job application to update
     * @param updateDto the new values for the job application
     * @return the updated job application
     * @throws jakarta.persistence.EntityNotFoundException if no job application is found with the given ID
     */
    @PutMapping("/jobs/{applicationId}")
    public JobApplicationResponseDTO updateApplication(@PathVariable int applicationId, @Valid @RequestBody UpdateJobApplicationDTO updateDto) {

        return jobApplicationService.update(applicationId, updateDto);

    }

    /**
     * Partially updates an existing job application.
     *
     * @param applicationId  the ID of the job application to patch
     * @param patchDto the fields to update
     * @return the updated job application
     * @throws jakarta.persistence.EntityNotFoundException if no job application is found with the given ID
     * @throws IllegalArgumentException if the patch data is invalid
     */
    @PatchMapping("/jobs/{applicationId}")
    public JobApplicationResponseDTO patchApplication(@PathVariable int applicationId, @RequestBody PatchJobApplicationDTO patchDto) {

        return jobApplicationService.patch(applicationId, patchDto);

    }

    /**
     * Deletes a job application by its ID.
     *
     * @param applicationId the ID of the job application to delete
     */
    @DeleteMapping("/jobs/{applicationId}")
    public void deleteApplication(@PathVariable int applicationId) {

        jobApplicationService.deleteById(applicationId);

    }


}
