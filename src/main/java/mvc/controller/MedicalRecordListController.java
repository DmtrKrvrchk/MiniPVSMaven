package mvc.controller;


import mvc.model.MedicalRecordModel;
import mvc.model.PatientModel;
import mvc.view.MedicalRecordCreateEditPanel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.swing.*;


public class MedicalRecordListController {
    private final PatientModel patient;


    public MedicalRecordListController(PatientModel patient) { this.patient = patient; }


    public void newMedicalRecord() {
        JFrame frame = new JFrame("Patientenakte anlegen");
        frame.setSize(400, 300);
        MedicalRecordCreateEditPanel medicalRecordCreateEditPanel = new MedicalRecordCreateEditPanel(frame, this.patient);
        frame.add(medicalRecordCreateEditPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void editMedicalRecord(MedicalRecordModel medicalRecordToEdit) {
        JFrame frame = new JFrame("Patientenakte bearbeiten");
        frame.setSize(400, 300);
        MedicalRecordCreateEditPanel medicalRecordCreateEditPanel = new MedicalRecordCreateEditPanel(frame, medicalRecordToEdit, this.patient);
        frame.add(medicalRecordCreateEditPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public boolean deleteMedicalRecord(MedicalRecordModel medicalRecordToDelete) {
        int choice = JOptionPane.showConfirmDialog(
                null,
                "Sind Sie sicher, dass Sie diese Patientenakte löschen möchten?",
                "Bestätigung des Löschvorgangs",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            //TODO in neue Klasse MedicalRecordManager auslagern und so schreiben, dass jedes mal eine neue EntityMagaerFactory erstellt wird

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
            EntityManager em = emf.createEntityManager();

            try {
                em.getTransaction().begin();
                MedicalRecordModel managedMedicalRecord = em.merge(medicalRecordToDelete);
                em.remove(managedMedicalRecord);
                em.getTransaction().commit();

                JOptionPane.showMessageDialog(
                        null,
                        "Patientenakte erfolgreich gelöscht!",
                        "Gelöscht!",
                        JOptionPane.INFORMATION_MESSAGE);

                return true;
            } catch (Exception e) {
                if (em.getTransaction() != null && em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }

                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Fehler beim Löschen der Patientenakte.",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE);

                return false;
            } finally {
                em.close();
                emf.close();
            }
        } else {
            return false;
        }
    }
}