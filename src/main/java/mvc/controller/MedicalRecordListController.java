package mvc.controller;

import mvc.model.MedicalRecordModel;
import mvc.model.PatientModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import mvc.view.MedicalRecordCreateEditPanel;

import javax.swing.*;


public class MedicalRecordListController {
    private final PatientModel patient;


    public MedicalRecordListController(PatientModel patient) { this.patient = patient; }


    public void newMedicalRecord() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            JFrame frame = new JFrame("Patientenakte anlegen");
            frame.setSize(400, 300);
            MedicalRecordCreateEditPanel medicalRecordCreateEditPanel = new MedicalRecordCreateEditPanel(frame, this.patient);
            frame.add(medicalRecordCreateEditPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);

            tx.commit();
        }
    }

    public void editMedicalRecord(MedicalRecordModel medicalRecordToEdit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            JFrame frame = new JFrame("Patientenakte bearbeiten");
            frame.setSize(400, 300);
            MedicalRecordCreateEditPanel medicalRecordCreateEditPanel = new MedicalRecordCreateEditPanel(frame, medicalRecordToEdit, this.patient);
            frame.add(medicalRecordCreateEditPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);

            tx.commit();
        }
    }

    public boolean deleteMedicalRecord(MedicalRecordModel medicalRecordToDelete) {
        int choice = JOptionPane.showConfirmDialog(null, "Sind Sie sicher, dass Sie diese Patientenakte löschen möchten?", "Bestätigung des Löschvorgangs", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction tx = session.beginTransaction();
                try {
                    session.delete(medicalRecordToDelete);
                    tx.commit();
                    JOptionPane.showMessageDialog(null, "Patientenakte erfolgreich gelöscht!", "Gelöscht!", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } catch (Exception e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}