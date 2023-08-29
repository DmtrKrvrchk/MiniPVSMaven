package mvc.model;


import jakarta.persistence.*;
import org.hibernate.Hibernate;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class PatientModel {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalRecordModel> medicalRecords;


    public PatientModel() {}
    public PatientModel(String firstName, String lastName, LocalDate dateOfBirth, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.medicalRecords = new ArrayList<>();
    }


    public List<MedicalRecordModel> getMedicalRecordsFromDatabase() {
        //TODO in neue Klasse MedicalRecordManager auslagern und so schreiben, dass jedes mal eine neue EntityMagaerFactory erstellt wird
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        if (!Hibernate.isInitialized(medicalRecords)) {
            medicalRecords = em.createQuery("SELECT m FROM MedicalRecordModel m WHERE m.patient = :patient", MedicalRecordModel.class)
                    .setParameter("patient", this)
                    .getResultList();
        }
        return medicalRecords;
    }
    public List<MedicalRecordModel> getMedicalRecords() { return medicalRecords; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}