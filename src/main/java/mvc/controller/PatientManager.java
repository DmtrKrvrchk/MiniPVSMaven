package mvc.controller;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import mvc.model.PatientModel;

import java.util.List;


public class PatientManager {

    public void createPatient(PatientModel patient) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(patient);
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

    public void updatePatient(PatientModel patient) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(patient);
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

    public boolean deletePatient(PatientModel patient) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            PatientModel managedPatient = em.merge(patient);
            em.remove(managedPatient);
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

    public List<PatientModel> getPatientsFromDatabase() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            List<PatientModel> patients = em.createQuery("SELECT p FROM PatientModel p", PatientModel.class)
                    .getResultList();

            em.getTransaction().commit();
            return patients;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
            emf.close();
        }
    }
}