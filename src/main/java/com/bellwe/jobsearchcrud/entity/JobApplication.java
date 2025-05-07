package com.bellwe.jobsearchcrud.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "job_application")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "company")
    private String company;

    @Column(name = "date_applied")
    private LocalDate dateApplied;

    @Column(name = "status", length = 100)
    private String status;

    @Column(name = "notes", length = 1000)
    private String notes;

    public JobApplication() {}

    public JobApplication(String jobTitle, String company, LocalDate dateApplied, String status, String notes) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.dateApplied = dateApplied;
        this.status = status;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "JobApplication{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                ", company='" + company + '\'' +
                ", dateApplied=" + dateApplied +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
