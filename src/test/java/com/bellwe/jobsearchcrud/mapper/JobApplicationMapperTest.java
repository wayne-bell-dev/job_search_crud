package com.bellwe.jobsearchcrud.mapper;

import com.bellwe.jobsearchcrud.dto.CreateJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.JobApplicationResponseDTO;
import com.bellwe.jobsearchcrud.dto.PatchJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.UpdateJobApplicationDTO;
import com.bellwe.jobsearchcrud.entity.JobApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JobApplicationMapperTest {

    private final JobApplicationMapper mapper = new JobApplicationMapper();

    private JobApplication entity1;

    private JobApplication entity2;

    private static final LocalDate JAN_1_2025 = LocalDate.of(2025, 1, 1);

    @Test
    void testToResponseDto_MapsEntityCorrectly() {

        entity1 = new JobApplication("Software Engineer", "Google", JAN_1_2025, "Applied", "Interview Pending");
        entity1.setId(1);

        JobApplicationResponseDTO dto = mapper.toResponseDto(entity1);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getCompany()).isEqualTo("Google");
        assertThat(dto.getJobTitle()).isEqualTo("Software Engineer");
        assertThat(dto.getDateApplied()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(dto.getStatus()).isEqualTo("Applied");
        assertThat(dto.getNotes()).isEqualTo("Interview Pending");
    }

    @Test
    void testToResponseDtoList_MapsListCorrectly() {

        entity1 = new JobApplication("Software Engineer", "Google", JAN_1_2025, "Applied", "Interview Pending");
        entity1.setId(1);

        entity2 = new JobApplication("Ice Cream Machine Repairman", "McDonalds", JAN_1_2025, "Processing", "Unlimited work");
        entity2.setId(2);

        List<JobApplicationResponseDTO> dtoList = mapper.toResponseDtoList(List.of(entity1, entity2));

        assertThat(dtoList).hasSize(2);
        assertThat(dtoList.get(0).getCompany()).isEqualTo("Google");
        assertThat(dtoList.get(1).getCompany()).isEqualTo("McDonalds");

    }

    @Test
    void testToJobApplication_MapsCreateDtoCorrectly() {
        CreateJobApplicationDTO dto = new CreateJobApplicationDTO();
        dto.setJobTitle("L5");
        dto.setCompany("Amazon");
        dto.setDateApplied(JAN_1_2025);
        dto.setStatus("Applied");
        dto.setNotes("Relocate to Washington");

        JobApplication entity = mapper.toJobApplication(dto);

        assertThat(entity.getCompany()).isEqualTo("Amazon");
        assertThat(entity.getJobTitle()).isEqualTo("L5");
        assertThat(entity.getDateApplied()).isEqualTo(JAN_1_2025);
        assertThat(entity.getStatus()).isEqualTo("Applied");
        assertThat(entity.getNotes()).isEqualTo("Relocate to Washington");
    }

    @Test
    void testUpdateJobApplication_OverwritesAllFields() {

        entity1 = new JobApplication("Software Engineer", "Google", JAN_1_2025, "Applied", "Interview Pending");
        entity1.setId(1);

        UpdateJobApplicationDTO dto = new UpdateJobApplicationDTO();
        dto.setJobTitle("DevOps");
        dto.setCompany("Meta");
        dto.setDateApplied(LocalDate.of(2025, 2, 1));
        dto.setStatus("Processing");
        dto.setNotes("Buy Meta goggles");

        mapper.updateJobApplication(entity1, dto);

        assertThat(entity1.getJobTitle()).isEqualTo("DevOps");
        assertThat(entity1.getCompany()).isEqualTo("Meta");
        assertThat(entity1.getDateApplied()).isEqualTo(LocalDate.of(2025, 2, 1));
        assertThat(entity1.getStatus()).isEqualTo("Processing");
        assertThat(entity1.getNotes()).isEqualTo("Buy Meta goggles");

    }

    @Test
    void testPatchJobApplication_UpdatesAllFields() {

        entity1 = new JobApplication("Software Engineer", "Google", JAN_1_2025, "Applied", "Interview Pending");
        entity1.setId(1);

        PatchJobApplicationDTO dto = new PatchJobApplicationDTO();
        dto.setJobTitle("DevOps");
        dto.setCompany("Meta");
        dto.setDateApplied(LocalDate.of(2025, 2, 1));
        dto.setStatus("Processing");
        dto.setNotes("Buy Meta goggles");

        mapper.patchJobApplication(entity1, dto);

        assertThat(entity1.getJobTitle()).isEqualTo("DevOps");
        assertThat(entity1.getCompany()).isEqualTo("Meta");
        assertThat(entity1.getDateApplied()).isEqualTo(LocalDate.of(2025, 2, 1));
        assertThat(entity1.getStatus()).isEqualTo("Processing");
        assertThat(entity1.getNotes()).isEqualTo("Buy Meta goggles");
    }

    @Test
    void testPatchJobApplication_UpdatesNonNullFields() {

        entity1 = new JobApplication("Software Engineer", "Google", JAN_1_2025, "Applied", "Interview Pending");
        entity1.setId(1);

        PatchJobApplicationDTO dto = new PatchJobApplicationDTO();
        dto.setJobTitle("Engineer");
        dto.setStatus("On Hold");

        mapper.patchJobApplication(entity1, dto);

        assertThat(entity1.getJobTitle()).isEqualTo("Engineer");
        assertThat(entity1.getCompany()).isEqualTo("Google");
        assertThat(entity1.getDateApplied()).isEqualTo(JAN_1_2025);
        assertThat(entity1.getStatus()).isEqualTo("On Hold");
        assertThat(entity1.getNotes()).isEqualTo("Interview Pending");
    }
}
