package mvc.model;


import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
public class MedicalRecordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private MedicalRecordType type;
    private String description;

    @Basic
    private long patient_id;


    public MedicalRecordModel() {}
    public MedicalRecordModel(LocalDate date, MedicalRecordType type, String description, long patient_id) {
        this.date = date;
        this.type = type;
        this.description = description;
        this.patient_id = patient_id;
    }


    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public MedicalRecordType getType() { return type; }
    public void setType(MedicalRecordType type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getId() { return id; }

    public void setPatientId(long patient) {
        this.patient_id = patient;
    }
}