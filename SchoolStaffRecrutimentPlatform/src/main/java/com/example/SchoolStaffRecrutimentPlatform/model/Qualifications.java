package com.example.SchoolStaffRecrutimentPlatform.model;

import jakarta.persistence.*;


@Entity
@Table(name = "Qualifications")
public class Qualifications {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qualification_id")
    private Long qualificationId;

    private String qualificationName;
    private String result;

    // The Users object to reference the associated user
    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    // Getters and Setters
    public Long getQualification_id() {
        return qualificationId;
    }


    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
