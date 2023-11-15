package mvc.model;


import jakarta.persistence.*;
import mvc.controller.MedicalRecordManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class PatientModel {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<MedicalRecordModel> medicalRecords = new ArrayList<>();


    public PatientModel() {}
    public PatientModel(String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }


    public List<MedicalRecordModel> getMedicalRecords() {
        MedicalRecordManager recordManager = new MedicalRecordManager();
        return recordManager.getMedicalRecordsForPatient(this);
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
}