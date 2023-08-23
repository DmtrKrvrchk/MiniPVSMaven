package mvc.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class MedicalRecordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private MedicalRecordType type;
    private String description;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private final PatientModel patient;


    public MedicalRecordModel(LocalDate date, MedicalRecordType type, String description, PatientModel patient) {
        this.date = date;
        this.type = type;
        this.description = description;
        this.patient = patient;
    }


    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public MedicalRecordType getType() { return type; }
    public void setType(MedicalRecordType type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}