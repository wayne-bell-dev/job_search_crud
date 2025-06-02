package com.bellwe.jobsearchcrud.service;

import com.bellwe.jobsearchcrud.dao.JobApplicationRepository;
import com.bellwe.jobsearchcrud.dto.CreateJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.JobApplicationResponseDTO;
import com.bellwe.jobsearchcrud.dto.PatchJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.UpdateJobApplicationDTO;
import com.bellwe.jobsearchcrud.entity.JobApplication;
import com.bellwe.jobsearchcrud.mapper.JobApplicationMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class JobApplicationServiceImplTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private JobApplicationMapper jobApplicationMapper;

    @InjectMocks
    private JobApplicationServiceImpl jobApplicationService;

    private JobApplication jobEntity1;
    private JobApplication jobEntity2;
    private JobApplicationResponseDTO responseDTO1;
    private JobApplicationResponseDTO responseDTO2;

    private static final LocalDate JAN_1_2025 = LocalDate.of(2025, 1, 1);

    @BeforeEach
    void setUp() {
        jobEntity1 = new JobApplication("Software Engineer", "Google", LocalDate.of(2025, 1, 1), "Applied", "Excited about this opportunity");
        jobEntity1.setId(1);

        jobEntity2 = new JobApplication("Ice Cream Machine Repairman", "McDonalds", LocalDate.of(2025, 2, 1), "Processing", "Unlimited work");
        jobEntity2.setId(2);

        responseDTO1 = new JobApplicationResponseDTO();
        responseDTO1.setId(1);
        responseDTO1.setCompany("Google");
        responseDTO1.setJobTitle("Software Engineer");
        responseDTO1.setDateApplied(JAN_1_2025);
        responseDTO1.setStatus("Applied");
        responseDTO1.setNotes("Excited about this opportunity");

        responseDTO2 = new JobApplicationResponseDTO();
        responseDTO2.setId(2);
        responseDTO2.setCompany("McDonalds");
        responseDTO2.setJobTitle("Ice Cream Machine Repairman");
        responseDTO2.setDateApplied(LocalDate.of(2025, 2, 1));
        responseDTO2.setStatus("Processing");
        responseDTO2.setNotes("Unlimited work");
    }

    @Test
    void findAll_shouldReturnListOfDtos() {
        List<JobApplication> entityList = List.of(jobEntity1, jobEntity2);
        List<JobApplicationResponseDTO> dtoList = List.of(responseDTO1, responseDTO2);

        when(jobApplicationRepository.findAll()).thenReturn(entityList);
        when(jobApplicationMapper.toResponseDtoList(entityList)).thenReturn(dtoList);

        List<JobApplicationResponseDTO> result = jobApplicationService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCompany()).isEqualTo("Google");
        assertThat(result.get(1).getCompany()).isEqualTo("McDonalds");
        verify(jobApplicationRepository).findAll();
        verify(jobApplicationMapper).toResponseDtoList(entityList);
    }

    @Test
    void findAll_shouldReturnEmptyList_whenRepositoryReturnsEmptyList() {
        when(jobApplicationRepository.findAll()).thenReturn(Collections.emptyList());
        when(jobApplicationMapper.toResponseDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<JobApplicationResponseDTO> result = jobApplicationService.findAll();

        assertThat(result).isEmpty();
        verify(jobApplicationRepository).findAll();
        verify(jobApplicationMapper).toResponseDtoList(Collections.emptyList());
    }

    @Test
    void findById_whenExists_shouldReturnDto() {
        when(jobApplicationRepository.findById(1)).thenReturn(Optional.of(jobEntity1));
        when(jobApplicationMapper.toResponseDto(jobEntity1)).thenReturn(responseDTO1);

        JobApplicationResponseDTO result = jobApplicationService.findById(1);

        assertThat(result.getCompany()).isEqualTo("Google");
    }

    @Test
    void findById_whenNotExists_shouldThrowEntityNotFoundException() {
        when(jobApplicationRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> jobApplicationService.findById(1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Did not find job application by id");
    }

    @Test
    void save_shouldMapSaveAndReturnDto() {
        CreateJobApplicationDTO createDto = new CreateJobApplicationDTO();
        createDto.setCompany("Google");
        createDto.setJobTitle("Software Engineer");
        createDto.setDateApplied(JAN_1_2025);
        createDto.setStatus("Applied");

        when(jobApplicationMapper.toJobApplication(createDto)).thenReturn(jobEntity1);
        when(jobApplicationRepository.save(jobEntity1)).thenReturn(jobEntity1);
        when(jobApplicationMapper.toResponseDto(jobEntity1)).thenReturn(responseDTO1);

        JobApplicationResponseDTO result = jobApplicationService.save(createDto);

        assertThat(result.getCompany()).isEqualTo("Google");
        verify(jobApplicationMapper).toJobApplication(createDto);
        verify(jobApplicationRepository).save(jobEntity1);
        verify(jobApplicationMapper).toResponseDto(jobEntity1);
    }

    @Test
    void update_whenExists_shouldApplyChangesAndReturnDto() {
        UpdateJobApplicationDTO updateDto = new UpdateJobApplicationDTO();
        updateDto.setCompany("Amazon");
        updateDto.setJobTitle("Backend Engineer");
        updateDto.setDateApplied(LocalDate.of(2025, 1, 5));
        updateDto.setStatus("Interview");

        when(jobApplicationRepository.findById(1)).thenReturn(Optional.of(jobEntity1));
        doNothing().when(jobApplicationMapper).updateJobApplication(jobEntity1, updateDto);
        when(jobApplicationRepository.save(jobEntity1)).thenReturn(jobEntity1);
        when(jobApplicationMapper.toResponseDto(jobEntity1)).thenReturn(responseDTO1);

        // update responseDTO1 fields for symmetry
        responseDTO1.setCompany("Amazon");
        responseDTO1.setJobTitle("Backend Engineer");
        responseDTO1.setDateApplied(LocalDate.of(2025, 1, 5));
        responseDTO1.setStatus("Interview");

        JobApplicationResponseDTO result = jobApplicationService.update(1, updateDto);

        assertThat(result.getCompany()).isEqualTo("Amazon"); // Based on mocked responseDTO
        verify(jobApplicationRepository).findById(1);
        verify(jobApplicationMapper).updateJobApplication(jobEntity1, updateDto);
        verify(jobApplicationRepository).save(jobEntity1);
        verify(jobApplicationMapper).toResponseDto(jobEntity1);
    }

    @Test
    void update_whenNotExists_shouldThrowEntityNotFoundException() {
        UpdateJobApplicationDTO updateDto = new UpdateJobApplicationDTO();
        updateDto.setCompany("Amazon");
        updateDto.setJobTitle("Backend Engineer");
        updateDto.setDateApplied(LocalDate.of(2025, 1, 5));
        updateDto.setStatus("Interview");

        when(jobApplicationRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> jobApplicationService.update(1, updateDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Did not find the job application by id");
        verify(jobApplicationRepository).findById(1);
    }

    @Test
    void patch_whenExists_shouldPatchFieldsAndReturnDto() {
        PatchJobApplicationDTO patchDto = mock(PatchJobApplicationDTO.class);
        doNothing().when(patchDto).validate();

        when(jobApplicationRepository.findById(1)).thenReturn(Optional.of(jobEntity1));
        doNothing().when(jobApplicationMapper).patchJobApplication(jobEntity1, patchDto);
        when(jobApplicationRepository.save(jobEntity1)).thenReturn(jobEntity1);
        when(jobApplicationMapper.toResponseDto(jobEntity1)).thenReturn(responseDTO1);

        JobApplicationResponseDTO result = jobApplicationService.patch(1, patchDto);

        assertThat(result.getCompany()).isEqualTo("Google");
        verify(patchDto).validate();
        verify(jobApplicationRepository).findById(1);
        verify(jobApplicationMapper).patchJobApplication(jobEntity1, patchDto);
        verify(jobApplicationRepository).save(jobEntity1);
        verify(jobApplicationMapper).toResponseDto(jobEntity1);
    }

    @Test
    void patch_whenNotExists_shouldThrowEntityNotFoundException() {
        PatchJobApplicationDTO patchDto = mock(PatchJobApplicationDTO.class);

        doNothing().when(patchDto).validate();
        when(jobApplicationRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> jobApplicationService.patch(1, patchDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Did not find the job application by id");

        verify(patchDto).validate();
        verify(jobApplicationRepository).findById(1);
    }

    @Test
    void deleteById_shouldCallRepository() {
        jobApplicationService.deleteById(1);
        verify(jobApplicationRepository).deleteById(1);
    }
}
