package com.bellwe.jobsearchcrud.controller;

import com.bellwe.jobsearchcrud.dto.CreateJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.JobApplicationResponseDTO;
import com.bellwe.jobsearchcrud.dto.PatchJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.UpdateJobApplicationDTO;
import com.bellwe.jobsearchcrud.service.JobApplicationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JobApplicationRestController {

    private JobApplicationService jobApplicationService;

    @Autowired
    public JobApplicationRestController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    // return all applications
    @GetMapping("/jobs")
    public List<JobApplicationResponseDTO> findAllApplications() {

        return jobApplicationService.findAll();

    }

    // return a single application by id
    @GetMapping("/jobs/{applicationId}")
    public JobApplicationResponseDTO findApplication(@PathVariable int applicationId) {

        return jobApplicationService.findById(applicationId);

    }

    // save an application
    @PostMapping("/jobs")
    public JobApplicationResponseDTO saveApplication(@Valid @RequestBody CreateJobApplicationDTO createDto) {

        return jobApplicationService.save(createDto);

    }

    // update an application
    @PutMapping("/jobs/{applicationId}")
    public JobApplicationResponseDTO updateApplication(@PathVariable int applicationId, @Valid @RequestBody UpdateJobApplicationDTO updateDto) {

        return jobApplicationService.update(applicationId, updateDto);

    }

    // patch an application
    @PatchMapping("/jobs/{applicationId}")
    public JobApplicationResponseDTO patchApplication(@PathVariable int applicationId, @RequestBody PatchJobApplicationDTO patchDto) {

        return jobApplicationService.patch(applicationId, patchDto);

    }

    // delete an application
    @DeleteMapping("/jobs/{applicationId}")
    public void deleteApplication(@PathVariable int applicationId) {

        jobApplicationService.deleteById(applicationId);

    }


}
