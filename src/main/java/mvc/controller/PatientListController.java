package mvc.controller;


import mvc.model.PatientModel;
import mvc.view.PatientCreateEditPanel;

import javax.swing.*;


public class PatientListController {
    private final PatientManager patientManager;


    public PatientListController() {
        this.patientManager = new PatientManager();
    }


    public void newPatient() {
        JFrame frame = new JFrame("Patient*in anlegen");
        frame.setSize(400, 300);
        frame.setResizable(false);
        PatientCreateEditPanel patientCreateEditPanel = new PatientCreateEditPanel(frame);
        frame.add(patientCreateEditPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void editPatient(PatientModel patientToEdit) {
        JFrame frame = new JFrame("Patienteninformation bearbeiten");
        frame.setSize(400, 300);
        frame.setResizable(false);
        PatientCreateEditPanel patientCreateEditPanel = new PatientCreateEditPanel(frame, patientToEdit);
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
            if (patientManager.deletePatient(patientToDelete)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Patienteninformation erfolgreich gelöscht!",
                        "Gelöscht!",
                        JOptionPane.INFORMATION_MESSAGE);

                return true;
            } else {
               return false;
            }
        } else {
            return false;
        }
    }
}