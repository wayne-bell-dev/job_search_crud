package com.bellwe.jobsearchcrud.dto;

import java.time.LocalDate;

public class PatchJobApplicationDTO {

    private String jobTitle;

    private String company;

    private LocalDate dateApplied;

    private String status;

    private String notes;

    // validate patch dto fields
    public void validate() {

        if (jobTitle != null && jobTitle.isBlank()) {
            throw new IllegalArgumentException("Job title cannot be blank");
        }
        if (company != null && company.isBlank()) {
            throw new IllegalArgumentException("Company cannot be blank");
        }
        if (dateApplied != null && dateApplied.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date applied must be in the past or present");
        }
        if (status != null && status.length() > 100) {
            throw new IllegalArgumentException("Status cannot exceed 100 characters");
        }
        if (notes != null && notes.length() > 1000) {
            throw new IllegalArgumentException("Notes cannot exceed 1000 characters");
        }
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getDateApplied() {
        return dateApplied;
    }

    public void setDateApplied(LocalDate dateApplied) {
        this.dateApplied = dateApplied;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
