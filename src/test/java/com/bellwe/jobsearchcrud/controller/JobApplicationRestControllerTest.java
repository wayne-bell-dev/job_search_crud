package com.bellwe.jobsearchcrud.controller;

import com.bellwe.jobsearchcrud.dto.CreateJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.JobApplicationResponseDTO;
import com.bellwe.jobsearchcrud.dto.PatchJobApplicationDTO;
import com.bellwe.jobsearchcrud.dto.UpdateJobApplicationDTO;
import com.bellwe.jobsearchcrud.service.JobApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobApplicationRestController.class)
public class JobApplicationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JobApplicationService jobApplicationService;

    private LocalDate dtoLocalDate = LocalDate.of(2025, 1, 1);

    private JobApplicationResponseDTO mockResponseDto = new JobApplicationResponseDTO();

    private CreateJobApplicationDTO mockCreateDto = new CreateJobApplicationDTO();

    private UpdateJobApplicationDTO mockUpdateDto = new UpdateJobApplicationDTO();

    private PatchJobApplicationDTO mockPatchDto = new PatchJobApplicationDTO();

    @BeforeEach
    void setup() {
        mockResponseDto.setId(1);
        mockResponseDto.setCompany("Google");

        mockCreateDto.setCompany("Google");
        mockCreateDto.setJobTitle("Application Developer");
        mockCreateDto.setDateApplied(dtoLocalDate);

        mockUpdateDto.setCompany("Google");
        mockUpdateDto.setJobTitle("Application Developer");
        mockUpdateDto.setDateApplied(dtoLocalDate);

        mockPatchDto.setCompany("Google");
        mockPatchDto.setJobTitle("Application Developer");
        mockPatchDto.setDateApplied(dtoLocalDate);

    }

    @Test
    void testFindAllApplications() throws Exception {


        Mockito
                .when(jobApplicationService.findAll())
                .thenReturn(Collections.singletonList(mockResponseDto));

        mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].company").value("Google"));

    }

    @Test
    void testFindApplicationById() throws Exception {

        Mockito
                .when(jobApplicationService.findById(1))
                .thenReturn(mockResponseDto);

        mockMvc.perform(get("/api/jobs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value("Google"));
    }

    @Test
    void testSaveApplication() throws Exception {

        Mockito
                .when(jobApplicationService.save(Mockito.any()))
                .thenReturn(mockResponseDto);

        mockMvc.perform(post("/api/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value("Google"));
    }

    @Test
    void testSaveApplication_InvalidInput_ReturnsBadRequestWithErrorResponse() throws Exception {

        CreateJobApplicationDTO invalidDto = new CreateJobApplicationDTO();

        mockMvc.perform(post("/api/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateApplication() throws Exception {

        Mockito
                .when(jobApplicationService.update(Mockito.anyInt(), Mockito.any()))
                .thenReturn(mockResponseDto);

        mockMvc.perform(put("/api/jobs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value("Google"));
    }

    @Test
    void testUpdateApplication_InvalidInput_ReturnsBadRequestWithErrorResponse() throws Exception {

        UpdateJobApplicationDTO invalidDto = new UpdateJobApplicationDTO();

        mockMvc.perform(put("/api/jobs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateApplication_EntityNotFound_Returns404() throws Exception {

        Mockito.when(jobApplicationService.update(Mockito.anyInt(), Mockito.any()))
                .thenThrow(new EntityNotFoundException("Application not found"));

        mockMvc.perform(put("/api/jobs/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockUpdateDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Application not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testPatchApplication() throws Exception {

        Mockito
                .when(jobApplicationService.patch(Mockito.anyInt(), Mockito.any()))
                .thenReturn(mockResponseDto);

        mockMvc.perform(patch("/api/jobs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockPatchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value("Google"));

    }

    @Test
    void testPatchApplication_IllegalArgument_Returns400() throws Exception {

        PatchJobApplicationDTO invalidDto = new PatchJobApplicationDTO();
        invalidDto.setCompany("");

        Mockito.when(jobApplicationService.patch(Mockito.anyInt(), Mockito.any()))
                        .thenThrow(new IllegalArgumentException("Company cannot be blank"));

        mockMvc.perform(patch("/api/jobs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testPatchApplication_EntityNotFound_Returns404() throws Exception {

        Mockito.when(jobApplicationService.patch(Mockito.anyInt(), Mockito.any()))
                .thenThrow(new EntityNotFoundException("Application not found"));

        mockMvc.perform(patch("/api/jobs/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockPatchDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Application not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testDeleteApplication() throws Exception {

        mockMvc.perform(delete("/api/jobs/1"))
                .andExpect(status().isOk());

        Mockito.verify(jobApplicationService).deleteById(1);
    }


}
