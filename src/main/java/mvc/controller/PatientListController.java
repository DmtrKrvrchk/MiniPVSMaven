package mvc.controller;

import mvc.model.PatientModel;
import mvc.view.PatientCreateEditPanel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityTransaction;
import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PatientListController {

    private final EntityManagerFactory emf;

    public PatientListController() {
        emf = Persistence.createEntityManagerFactory("EntityManager");
    }


    private void handleTransaction(EntityManager em, Runnable action) {
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            action.run();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void newPatient() {
        JFrame frame = new JFrame("Patient*in anlegen");
        frame.setSize(400, 300);
        PatientCreateEditPanel patientCreateEditPanel = new PatientCreateEditPanel(frame, emf);
        frame.add(patientCreateEditPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void editPatient(PatientModel patientToEdit) {
        JFrame frame = new JFrame("Patienteninformation bearbeiten");
        frame.setSize(400, 300);
        PatientCreateEditPanel patientCreateEditPanel = new PatientCreateEditPanel(frame, patientToEdit, emf);
        frame.add(patientCreateEditPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public boolean deletePatient(PatientModel patientToDelete) {
        int choice = JOptionPane.showConfirmDialog(null, "Sind Sie sicher, dass Sie diese Patienteninformation löschen möchten?", "Bestätigung des Löschvorgangs", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            EntityManager em = emf.createEntityManager();
            AtomicBoolean success = new AtomicBoolean(false);

            try {
                handleTransaction(em, () -> {
                    PatientModel patient = em.merge(patientToDelete);
                    em.remove(patient);
                    success.set(true);
                });
            } finally {
                em.close();
            }

            if (success.get()) {
                JOptionPane.showMessageDialog(null, "Patienteninformation erfolgreich gelöscht!", "Gelöscht!", JOptionPane.INFORMATION_MESSAGE);
            }
            return success.get();
        } else {
            return false;
        }
    }
}

    /*public void newPatient() {
        EntityManager em = emf.createEntityManager();
        handleTransaction(em, () -> {
            JFrame frame = new JFrame("Patient*in anlegen");
            frame.setSize(400, 300);
            PatientCreateEditPanel patientCreateEditPanel = new PatientCreateEditPanel(frame, emf);
            frame.add(patientCreateEditPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public void editPatient(PatientModel patientToEdit) {
        EntityManager em = emf.createEntityManager();
        //em.persist();//um neues abzuspeichern
        //em.merge();//update
        //em.remove();//löschen
        //em.createNativeQuery().getResultList()// select * from PatientModel, Patient,
        handleTransaction(em, () -> {
            JFrame frame = new JFrame("Patienteninformation bearbeiten");
            frame.setSize(400, 300);
            PatientCreateEditPanel patientCreateEditPanel = new PatientCreateEditPanel(frame, patientToEdit, emf);
            frame.add(patientCreateEditPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public boolean deletePatient(PatientModel patientToDelete) {
        int choice = JOptionPane.showConfirmDialog(null, "Sind Sie sicher, dass Sie diese Patienteninformation löschen möchten?", "Bestätigung des Löschvorgangs", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            EntityManager em = emf.createEntityManager();
            AtomicBoolean success = new AtomicBoolean(false);

            try {
                handleTransaction(em, () -> {
                    PatientModel patient = em.merge(patientToDelete);
                    em.remove(patient);
                    success.set(true);
                });
            } finally {
                em.close();
            }

            if (success.get()) {
                JOptionPane.showMessageDialog(null, "Patienteninformation erfolgreich gelöscht!", "Gelöscht!", JOptionPane.INFORMATION_MESSAGE);
            }
            return success.get();
        } else {
            return false;
        }
    }*/