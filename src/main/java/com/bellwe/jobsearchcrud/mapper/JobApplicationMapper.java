package com.bellwe.jobsearchcrud.mapper;

import com.bellwe.jobsearchcrud.dto.CreateJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.JobApplicationResponseDTO;
import com.bellwe.jobsearchcrud.dto.PatchJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.UpdateJobApplicationDTO;
import com.bellwe.jobsearchcrud.entity.JobApplication;

import java.util.List;
import java.util.stream.Collectors;

public class JobApplicationMapper {

    // return a response DTO from the JobApplication entity
    public static JobApplicationResponseDTO toResponseDto(JobApplication jobApplication) {

        if (jobApplication == null) return null;

        JobApplicationResponseDTO dto = new JobApplicationResponseDTO();
        dto.setId(jobApplication.getId());
        dto.setJobTitle(jobApplication.getJobTitle());
        dto.setCompany(jobApplication.getCompany());
        dto.setDateApplied(jobApplication.getDateApplied());
        dto.setStatus(jobApplication.getStatus());
        dto.setNotes(jobApplication.getNotes());

        return dto;
    }

    // return a list of response DTOs from a list of JobApplication entities
    public static List<JobApplicationResponseDTO> toResponseDtoList(List<JobApplication> jobApplicationList) {

        return jobApplicationList.stream()
                .map(JobApplicationMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // return a JobApplication entity from a Create DTO
    public static JobApplication toJobApplication(CreateJobApplicationDTO dto) {

        if (dto == null) return null;

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobTitle(dto.getJobTitle());
        jobApplication.setCompany(dto.getCompany());
        jobApplication.setDateApplied(dto.getDateApplied());
        jobApplication.setStatus(dto.getStatus());
        jobApplication.setNotes(dto.getNotes());

        return jobApplication;
    }

    // update a JobApplication entity from an Update DTO
    public static void updateJobApplication(JobApplication jobApplication, UpdateJobApplicationDTO dto) {

        jobApplication.setJobTitle(dto.getJobTitle());
        jobApplication.setCompany(dto.getCompany());
        jobApplication.setDateApplied(dto.getDateApplied());
        jobApplication.setStatus(dto.getStatus());
        jobApplication.setNotes(dto.getNotes());

    }

    // patch a JobApplication entity from a Patch DTO
    public static void patchJobApplication(JobApplication jobApplication, PatchJobApplicationDTO dto) {

        if (dto.getJobTitle() != null) jobApplication.setJobTitle(dto.getJobTitle());
        if (dto.getCompany() != null) jobApplication.setCompany(dto.getCompany());
        if (dto.getDateApplied() != null) jobApplication.setDateApplied(dto.getDateApplied());
        if (dto.getStatus() != null) jobApplication.setStatus(dto.getStatus());
        if (dto.getNotes() != null) jobApplication.setNotes(dto.getNotes());

    }
}
