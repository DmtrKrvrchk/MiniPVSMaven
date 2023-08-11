package controller;

import model.MedicalRecordModel;
import model.PatientModel;
import org.hibernate.Session;
import util.HibernateUtil;
import view.MedicalRecordCreateEditPanel;

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
        int choice = JOptionPane.showConfirmDialog(null, "Sind Sie sicher, dass Sie diese Patientenakte löschen möchten?", "Bestätigung des Löschvorgangs", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            try {
                session.getTransaction().begin();
                session.delete(medicalRecordToDelete);
                session.getTransaction().commit();
            } catch (Exception e) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
                e.printStackTrace();
                return false;
            } finally {
                session.close();
            }
            JOptionPane.showMessageDialog(null, "Patientenakte erfolgreich gelöscht!", "Gelöscht!", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            return false;
        }
    }
}