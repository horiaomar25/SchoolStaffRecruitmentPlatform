package com.example.SchoolStaffRecrutimentPlatform.converter;

import com.example.SchoolStaffRecrutimentPlatform.dto.WorkHistoryDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.Profile;
import com.example.SchoolStaffRecrutimentPlatform.entities.School;
import com.example.SchoolStaffRecrutimentPlatform.entities.WorkHistory;
import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WorkHistoryConverter {

    @Autowired
    SchoolRepository schoolRepository;

    public WorkHistory convertDTOToEntity(WorkHistoryDTO dto, Profile profile) {
        WorkHistory workHistory = new WorkHistory();

        workHistory.setRole(dto.getRole());
        workHistory.setDuration(dto.getDuration());

        Optional<School> school = schoolRepository.findById(dto.getSchoolId());

        if(school.isPresent()) {
            workHistory.setSchool(school.get());
        } else {
            throw new RuntimeException("School not found");

        }

        return workHistory;

    }

    public List<WorkHistory> convertDTOListToEntity(List<WorkHistoryDTO> workHistoryDtoList, Profile profile) {
        return workHistoryDtoList.stream().map(dto -> convertDTOToEntity(dto, profile)).collect(Collectors.toList());
    }

    // Convert a single entity into a DTO
    public WorkHistoryDTO convertEntityToDTO(WorkHistory workHistory) {

        WorkHistoryDTO workHistoryDTO = new WorkHistoryDTO();

        workHistoryDTO.setRole(workHistory.getRole());

        workHistoryDTO.setDuration(workHistory.getDuration());

        workHistoryDTO.setSchoolId(workHistory.getSchool().getId());

        return workHistoryDTO;


    }


    public List<WorkHistoryDTO> convertEntityListToDTOList(List<WorkHistory> workHistoryList) {
        return workHistoryList.stream().map(workHistory -> convertEntityToDTO(workHistory)).collect(Collectors.toList());


    }


}
