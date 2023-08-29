package mvc.controller;


import mvc.model.PatientModel;
import mvc.view.PatientCreateEditPanel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.swing.*;


public class PatientListController {
    private final EntityManagerFactory emf;


    public PatientListController() {
        //TODO in neue Klasse Patientmanager auslagern und so schreiben, dass jedes mal eine neue EntityMagaerFactory erstellt wird
        emf = Persistence.createEntityManagerFactory("EntityManager");
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
        int choice = JOptionPane.showConfirmDialog(
                null,
                "Sind Sie sicher, dass Sie diese Patienteninformation löschen möchten?",
                "Bestätigung des Löschvorgangs",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            //TODO in neue Klasse MedicalRecordManager auslagern und so schreiben, dass jedes mal eine neue EntityMagaerFactory erstellt wird
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
            EntityManager em = emf.createEntityManager();

            try {
                em.getTransaction().begin();
                PatientModel patient = em.merge(patientToDelete);
                em.remove(patient);
                em.getTransaction().commit();

                JOptionPane.showMessageDialog(
                        null,
                        "Patienteninformation erfolgreich gelöscht!",
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
                        "Fehler beim Löschen der Patienteninformation.",
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