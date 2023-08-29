package mvc.controller;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import mvc.model.MedicalRecordModel;
import mvc.model.PatientModel;

import java.util.List;


public class MedicalRecordManager {

    public void createMedicalRecord(MedicalRecordModel medicalRecord) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(medicalRecord);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    public void updateMedicalRecord(MedicalRecordModel medicalRecord) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(medicalRecord);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    public boolean deleteMedicalRecord(MedicalRecordModel medicalRecord) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            MedicalRecordModel managedMedicalRecord = em.merge(medicalRecord);
            em.remove(managedMedicalRecord);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
            emf.close();
        }
    }

    public List<MedicalRecordModel> getMedicalRecordsForPatient(PatientModel patient) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<MedicalRecordModel> medicalRecords = em.createQuery("SELECT m FROM MedicalRecordModel m WHERE m.patient = :patient", MedicalRecordModel.class)
                    .setParameter("patient", patient)
                    .getResultList();

            em.getTransaction().commit();
            return medicalRecords;
        } finally {
            em.close();
            emf.close();
        }
    }
}