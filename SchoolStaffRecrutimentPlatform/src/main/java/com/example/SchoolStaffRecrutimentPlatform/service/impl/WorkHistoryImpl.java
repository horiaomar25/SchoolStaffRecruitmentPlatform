package com.example.SchoolStaffRecrutimentPlatform.service.impl;

import com.example.SchoolStaffRecrutimentPlatform.dto.WorkHistoryDTO;
import com.example.SchoolStaffRecrutimentPlatform.entities.Profile;
import com.example.SchoolStaffRecrutimentPlatform.entities.School;
import com.example.SchoolStaffRecrutimentPlatform.entities.WorkHistory;
import com.example.SchoolStaffRecrutimentPlatform.repository.SchoolRepository;
import com.example.SchoolStaffRecrutimentPlatform.repository.WorkHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkHistoryImpl {

    @Autowired
    WorkHistoryRepository workHistoryRepo;
    @Autowired
    private SchoolRepository schoolRepository;

    public String addWorkHistory(List<WorkHistoryDTO> workHistoryDTOList, Profile profile){
        // Represents List of WorkHistory Entity
    List<WorkHistory> newWorkHistoryList = new ArrayList<>();

        for(WorkHistoryDTO dto: workHistoryDTOList ){

            WorkHistory workHistory = new WorkHistory();

            workHistory.setSchoolName(dto.getSchoolName());
            workHistory.setRole(dto.getRole());
            workHistory.setDuration(dto.getDuration());
            workHistory.setProfile(profile);

            Optional<School> schoolOptional = schoolRepository.findById(dto.getSchoolId());

            // Check for the Optional
            if(schoolOptional.isPresent()){
                School school = schoolOptional.get();
                workHistory.setSchool(school);
            } else {
                return "School Not Found";
            }

            newWorkHistoryList.add(workHistory);
        }

        workHistoryRepo.saveAll(newWorkHistoryList);

        return "Work History Added/Created Successfully";
    }
}
