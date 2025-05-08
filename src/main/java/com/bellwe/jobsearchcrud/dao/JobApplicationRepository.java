package com.bellwe.jobsearchcrud.dao;

import com.bellwe.jobsearchcrud.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    /*
    Inherits the following methods from JpaRepository:
        save(S entity)
        findById(ID id)
        existsById(ID id)
        findAll()
        count()
        deleteById(ID id)
        deleteAll()
     */
}
