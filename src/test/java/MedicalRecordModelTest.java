import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import mvc.model.Gender;
import mvc.model.MedicalRecordModel;
import mvc.model.MedicalRecordType;
import mvc.model.PatientModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicalRecordModelTest {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
    private EntityManager em;
    private EntityTransaction transaction;

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();
        transaction.begin();
    }

    @AfterEach
    public void tearDown() {
        transaction.rollback();
        em.close();
    }

    @Test
    public void testGetMedicalRecords() {
        PatientModel patient = new PatientModel("Patient", "Test", LocalDate.of(2001, 1, 1), Gender.MALE);
        em.persist(patient);

        MedicalRecordModel medicalRecord = new MedicalRecordModel(LocalDate.now(), MedicalRecordType.SCHEIN, "Test", patient);
        em.persist(medicalRecord);

        List<MedicalRecordModel> medicalRecords = patient.getMedicalRecords();

        assertEquals(1, medicalRecords.size());
        assertEquals(medicalRecord.getId(), medicalRecords.get(0).getId());
    }
}