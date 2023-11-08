package mvc.controller;


import mvc.model.MedicalRecordModel;
import mvc.model.PatientModel;
import mvc.view.MedicalRecordCreateEditPanel;
import mvc.view.MedicalRecordListView;

import javax.swing.*;


public class MedicalRecordListController {
    private final PatientModel patient;
    private final MedicalRecordManager medicalRecordManager;
    private final MedicalRecordListView view;


    public MedicalRecordListController(PatientModel patient, MedicalRecordListView view) {
        this.patient = patient;
        this.view = view;
        this.medicalRecordManager = new MedicalRecordManager();
    }


    public void newMedicalRecord() {
        JFrame frame = new JFrame("Patientenakte anlegen");
        frame.setSize(400, 300);
        MedicalRecordCreateEditPanel medicalRecordCreateEditPanel = new MedicalRecordCreateEditPanel(frame, this.patient, view);
        frame.add(medicalRecordCreateEditPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void editMedicalRecord(MedicalRecordModel medicalRecordToEdit) {
        JFrame frame = new JFrame("Patientenakte bearbeiten");
        frame.setSize(400, 300);
        MedicalRecordCreateEditPanel medicalRecordCreateEditPanel = new MedicalRecordCreateEditPanel(frame, medicalRecordToEdit, this.patient, view);
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
            if (medicalRecordManager.deleteMedicalRecord(medicalRecordToDelete)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Patientenakte erfolgreich gelöscht!",
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