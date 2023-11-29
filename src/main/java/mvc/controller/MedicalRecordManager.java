package mvc.controller;


import jakarta.persistence.EntityManager;
import mvc.model.MedicalRecordModel;
import mvc.model.PatientModel;

import java.util.List;


public class MedicalRecordManager {
    public static void createMedicalRecord(MedicalRecordModel medicalRecord) {
        EntityManager em = CurrentSession.getInstance().getEntityManager();
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
        }
    }

    public static void updateMedicalRecord(MedicalRecordModel medicalRecord) {
        EntityManager em = CurrentSession.getInstance().getEntityManager();
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
        }
    }


    public static boolean deleteMedicalRecord(MedicalRecordModel medicalRecord) {
        EntityManager em = CurrentSession.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            medicalRecord = em.find(MedicalRecordModel.class, medicalRecord.getId());
            if (medicalRecord != null) {
                em.remove(medicalRecord);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().commit();
            return false;
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }


    public static List<MedicalRecordModel> getMedicalRecordsForPatient(PatientModel patient) {
        EntityManager em = CurrentSession.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();

            List<MedicalRecordModel> medicalRecords = em.createQuery("SELECT m FROM MedicalRecordModel m WHERE m.patient_id = :patient_id", MedicalRecordModel.class)
                    .setParameter("patient_id", patient.getId())
                    .getResultList();

            em.getTransaction().commit();
            return medicalRecords;
        } finally {
            em.close();
        }
    }
}