package com.example.SchoolStaffRecrutimentPlatform.service;

import com.example.SchoolStaffRecrutimentPlatform.converter.AssignmentConverter;
import com.example.SchoolStaffRecrutimentPlatform.converter.TimeSheetConverter;
import com.example.SchoolStaffRecrutimentPlatform.dto.AppUserDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.AssignmentDTO;
import com.example.SchoolStaffRecrutimentPlatform.dto.TimeSheetDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.AppUser;
import com.example.SchoolStaffRecrutimentPlatform.entities.Assignment;
import com.example.SchoolStaffRecrutimentPlatform.entities.TimeSheet;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.AssignmentNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.exceptions.UserNotFoundException;
import com.example.SchoolStaffRecrutimentPlatform.repository.AppUserRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.AssignmentRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.TimeSheetRepository;
import com.example.SchoolStaffRecrutimentPlatform.service.impl.AssignmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AssignmentServiceImplTest {

    @Mock
    AssignmentRepository assignmentRepository;

    @Mock
    TimeSheetRepository timeSheetRepository;

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    TimeSheetConverter timeSheetConverter;

    @Mock
    AssignmentConverter assignmentConverter;

    @InjectMocks
    AssignmentServiceImpl assignmentServiceImpl;

    // Happy Path
    @Test
    void acceptAssignment_WhenAssignmentAndUserExists_AcceptAssignment() throws UserNotFoundException {
        // Arrange
        int appUserId = 1;
        int assignmentId = 1;

        AppUser appUser = new AppUser();
        appUser.setId(appUserId);

        Assignment assignment = new Assignment(); // Assignment users_id initially set to null until user accepts assignment
        assignment.setId(assignmentId);
        assignment.setUser(null);

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

        when(appUserRepository.findById(appUserId)).thenReturn(Optional.of(appUser));

        when(assignmentRepository.save(assignment)).thenReturn(assignment);

        // Act
        Assignment result = assignmentServiceImpl.acceptAssignment(appUserId, assignmentId);


        // Assert
        assertEquals(appUser, result.getUser()); // The user is associated with assignment when user was null in the assignment entity initially
    }

    @Test
    void acceptAssignment_WhenAssignmentDoesNotExist_ThrowAssignmentException() throws UserNotFoundException {
        // Arrange
        int appUserId = 1;
        int assignmentId = 1;

        AppUser appUser = new AppUser();
        appUser.setId(appUserId);

        Assignment assignment = new Assignment(); // Assignment users_id initially set to null until user accepts assignment
        assignment.setId(assignmentId);
        assignment.setUser(null);

        // Act
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());


        AssignmentNotFoundException expecetedAssignmentException = assertThrows(AssignmentNotFoundException.class, () -> assignmentServiceImpl.acceptAssignment(appUserId, assignmentId));


        // Assert
        assertEquals("Assignment not found", expecetedAssignmentException.getMessage());



    }

    @Test
    void acceptAssignment_WhenUserDoesNotExist_ThrowUserNotFoundException() throws UserNotFoundException {
        int appUserId = 1;
        int assignmentId = 1;
        AppUser appUser = new AppUser();
        appUser.setId(appUserId);
        Assignment assignment = new Assignment();
        assignment.setId(assignmentId);
        assignment.setUser(null);

        // Act
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(appUserRepository.findById(appUserId)).thenReturn(Optional.empty());

        UserNotFoundException expectedUserException = assertThrows(UserNotFoundException.class, () -> assignmentServiceImpl.acceptAssignment(appUserId, assignmentId));

        assertEquals("User not found", expectedUserException.getMessage());

    }


    // Happy Path
    @Test
    void createTimeSheet_WhenAssignmentAndUserExists_CreateTimeSheet() throws UserNotFoundException {
        // Arrange
        int assignmentId = 1;
        int appUserId = 1;

        AppUser appUser = new AppUser();
        appUser.setId(appUserId);

        Assignment assignment = new Assignment();
        assignment.setId(assignmentId);
        assignment.setUser(appUser);
        assignment.setStartDate(LocalDate.of(2025, 2, 1));
        assignment.setEndDate(LocalDate.of(2025, 2, 8));

        // Expected saved TimeSheet
        TimeSheet savedTimeSheet = new TimeSheet();
        savedTimeSheet.setId(10);  // Simulated database-generated ID

        // Expected DTO
        TimeSheetDTO timeSheetDTO = new TimeSheetDTO();
        timeSheetDTO.setId(10);

        // Mocking behavior
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        // any Timesheet entity will returned as saved after the save method
        when(timeSheetRepository.save(any(TimeSheet.class))).thenReturn(savedTimeSheet);
        when(timeSheetConverter.convertTimeSheetToTimeSheetDTO(savedTimeSheet)).thenReturn(timeSheetDTO);

        // Act
        TimeSheetDTO result = assignmentServiceImpl.createTimeSheet(assignmentId);

        // Assert
        assertNotNull(result);
        assertEquals(savedTimeSheet.getId(), result.getId());





    }

    @Test
    void createTimeSheet_WhenAssignmentDoesNotExist_ThrowException() {
        // Arrange
        int assignmentId = 1;

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        // Act
        AssignmentNotFoundException expectedException = assertThrows(AssignmentNotFoundException.class, () -> assignmentServiceImpl.createTimeSheet(assignmentId));
        // Assert
        assertEquals("Assignment not found", expectedException.getMessage());

    }

   // Happy path
    @Test
    void getAcceptedAssignment_WhenAssignmentAndUserExists_GetAcceptedAssignment() throws UserNotFoundException {
        // Arrange
        int appUserId = 1;
        int assignmentId = 1;

        AppUser appUser = new AppUser();
        appUser.setId(appUserId);

        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setId(appUserId);

        Assignment assignment = new Assignment();
        assignment.setId(assignmentId);
        assignment.setUser(appUser);

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setAppUserDTO(appUserDTO);

        // Act
        when(appUserRepository.findById(appUserId)).thenReturn(Optional.of(appUser));
        when(assignmentRepository.findByUser(appUser)).thenReturn(assignment);
        when(assignmentConverter.convertEntityToDto(assignment)).thenReturn(assignmentDTO);

        // Assert
        AssignmentDTO results = assignmentServiceImpl.getAcceptedAssignment(appUserId);

        assertEquals(assignmentDTO,results );

    }


    @Test
    void getAcceptedAssignment_WhenAssignmentDoesNotExist_ThrowException() throws UserNotFoundException {

        int appUserId = 1;
        AppUser appUser = new AppUser();
        appUser.setId(appUserId);


        when(appUserRepository.findById(appUserId)).thenReturn(Optional.of(appUser)); // User found

        Assignment assignment = new Assignment();

        when(assignmentRepository.findByUser(appUser)).thenReturn(null);

        AssignmentNotFoundException expectedException = assertThrows(AssignmentNotFoundException.class, () -> assignmentServiceImpl.getAcceptedAssignment(appUserId));

        assertEquals("No assignment found for this user", expectedException.getMessage());

    }

    @Test
    void getAcceptedAssignment_WhenUserDoesNotExist_ThrowUserNotFoundException() throws UserNotFoundException {
         int appUserId = 1;

        when(appUserRepository.findById(appUserId)).thenReturn(Optional.empty());

        UserNotFoundException expectedException = assertThrows(UserNotFoundException.class, () -> assignmentServiceImpl.getAcceptedAssignment(appUserId));

        assertEquals("User not found", expectedException.getMessage());



    }


}
