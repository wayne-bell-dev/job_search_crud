CREATE TABLE job_application (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 job_title VARCHAR(255),
                                 company VARCHAR(255),
                                 date_applied DATE,
                                 status VARCHAR(100),
                                 notes VARCHAR(1000)
);
