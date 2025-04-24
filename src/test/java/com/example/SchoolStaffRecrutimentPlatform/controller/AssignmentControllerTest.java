package com.example.SchoolStaffRecrutimentPlatform.controller;

import com.example.SchoolStaffRecrutimentPlatform.converter.TimeSheetConverter;
import com.example.SchoolStaffRecrutimentPlatform.dto.AssignmentDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AssignmentRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.TimeSheetRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.impl.AssignmentServiceImpl;
import com.example.SchoolStaffRecrutimentPlatform.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Web Layer Test
@WebMvcTest(AssignmentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AssignmentServiceImpl assignmentService;

    @MockitoBean
    private AssignmentRepository assignmentRepository;

    @MockitoBean
    private AppUserRepository appUserRepository;

    @MockitoBean
    private TimeSheetRepository timeSheetRepository;

    @MockitoBean
    TimeSheetConverter timeSheetConverter;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private AssignmentDTO assignmentDTO;

    @Test
    public void getUnassignedAssignments_WhenAssignmentsExist_ReturnsOk() throws Exception {
        // Arrange
        Assignment assignment1 = new Assignment();
        assignment1.setAssignmentPosition("Year 1 Teacher");
        assignment1.setAssignmentDescription("""
                As a Year 1 Teacher, you will play a vital role at Vicarage Primary School in providing a stimulating and supportive learning environment for young children in their early stages of education. You will be responsible for delivering high-quality teaching, fostering a love for learning, and ensuring the academic, social, and emotional development of each child in your class.
                Key Responsibilities: You will be responsible for curriculum delivery by planning, preparing, and delivering engaging lessons in line with the national curriculum and school policies.""");
        assignment1.setStartDate(LocalDate.of(2025, 2, 17));
        assignment1.setEndDate(LocalDate.of(2025, 2, 21));

        Assignment assignment2 = new Assignment();
        assignment2.setAssignmentPosition("Year 1 Teacher");
        assignment2.setAssignmentDescription("""
                As a Year 1 Teacher, you will play a vital role at Vicarage Primary School in providing a stimulating and supportive learning environment for young children in their early stages of education. You will be responsible for delivering high-quality teaching, fostering a love for learning, and ensuring the academic, social, and emotional development of each child in your class.
                Key Responsibilities: You will be responsible for curriculum delivery by planning, preparing, and delivering engaging lessons in line with the national curriculum and school policies.""");
        assignment2.setStartDate(LocalDate.of(2025, 2, 17));
        assignment2.setEndDate(LocalDate.of(2025, 2, 21));


        List<Assignment> mockAssignment = Arrays.asList(assignment1,assignment2);

        when(assignmentRepository.findByUserIsNull()).thenReturn(mockAssignment);

        // Act & Assert
        mockMvc.perform(get("/api/v1/assignments/unassigned"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    public void acceptAssignment_UserIdIsSet_ReturnAssignmentAcceptedByUser() throws Exception {
        // Arrange
        String userName = "johnDoe";
        String token = "valid.jwt.token";

        AppUser mockAppUser = new AppUser();
        mockAppUser.setId(1);
        mockAppUser.setUsername(userName);

        Assignment assignment1 = new Assignment();
        assignment1.setAssignmentPosition("Year 1 Teacher");
        assignment1.setId(1);
        assignment1.setAssignmentDescription("""
            As a Year 1 Teacher, you will play a vital role at Vicarage Primary School...
            """);
        assignment1.setStartDate(LocalDate.of(2025, 2, 17));
        assignment1.setEndDate(LocalDate.of(2025, 2, 21));

        // Mock JWT and Repository behavior
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(token)).thenReturn(userName);
        when(appUserRepository.findByUsername(userName)).thenReturn(mockAppUser);
        when(assignmentService.acceptAssignment(mockAppUser.getId(), assignment1.getId()))
                .thenReturn(assignment1);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/assignments/{assignmentId}/accept", assignment1.getId())
                        .header("Authorization", "Bearer " + token)
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignmentPosition").value("Year 1 Teacher"))
                .andExpect(jsonPath("$.id").value(1));

    }

    // Get accepted assignment
    @Test
    public void getAcceptedAssignment_whenUserIdSet_returnAcceptedAssignment() throws Exception{
        // Arrange
        String userName = "johnDoe";
        String token = "valid.jwt.token";

        AppUser mockAppUser = new AppUser();
        mockAppUser.setId(1);
        mockAppUser.setUsername(userName);


        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setAssignmentId(1);
        assignmentDTO.setAssignmentPosition("Year 1 Teacher");
        assignmentDTO.setAssignmentDescription("As a Year 1 Teacher...");
        assignmentDTO.setStartDate(LocalDate.of(2025, 2, 17));
        assignmentDTO.setEndDate(LocalDate.of(2025, 2, 21));

        // Mock repository
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(token)).thenReturn(userName);
        when(appUserRepository.findByUsername(userName)).thenReturn(mockAppUser);
        when(assignmentService.getAcceptedAssignment(mockAppUser.getId())).thenReturn(assignmentDTO);

        // Act & Assert
        mockMvc.perform(get("/api/v1/assignments/accepted")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignmentPosition").value("Year 1 Teacher"))
                .andExpect(jsonPath("$.assignmentId").value(1));


    }

}
