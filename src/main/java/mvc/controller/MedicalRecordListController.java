package mvc.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import mvc.model.MedicalRecordModel;
import mvc.model.PatientModel;
import mvc.view.MedicalRecordCreateEditPanel;

import javax.swing.*;


public class MedicalRecordListController {
    private final PatientModel patient;


    public MedicalRecordListController(PatientModel patient) { this.patient = patient; }


    private void handleWithEntityManager(EntityManagerOperation operation) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            operation.perform(em);
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


    public void newMedicalRecord() {
        handleWithEntityManager(em -> {
            JFrame frame = new JFrame("Patientenakte anlegen");
            frame.setSize(400, 300);
            MedicalRecordCreateEditPanel medicalRecordCreateEditPanel = new MedicalRecordCreateEditPanel(frame, this.patient);
            frame.add(medicalRecordCreateEditPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public void editMedicalRecord(MedicalRecordModel medicalRecordToEdit) {
        handleWithEntityManager(em -> {
            JFrame frame = new JFrame("Patientenakte bearbeiten");
            frame.setSize(400, 300);
            MedicalRecordCreateEditPanel medicalRecordCreateEditPanel = new MedicalRecordCreateEditPanel(frame, medicalRecordToEdit, this.patient);
            frame.add(medicalRecordCreateEditPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public boolean deleteMedicalRecord(MedicalRecordModel medicalRecordToDelete) {
        int choice = JOptionPane.showConfirmDialog(null, "Sind Sie sicher, dass Sie diese Patientenakte löschen möchten?", "Bestätigung des Löschvorgangs", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            handleWithEntityManager(em -> {
                em.remove(em.contains(medicalRecordToDelete) ? medicalRecordToDelete : em.merge(medicalRecordToDelete));
            });
            JOptionPane.showMessageDialog(null, "Patientenakte erfolgreich gelöscht!", "Gelöscht!", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    interface EntityManagerOperation { void perform(EntityManager em); }
}