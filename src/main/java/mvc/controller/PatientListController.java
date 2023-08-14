package mvc.controller;

import mvc.model.PatientModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import mvc.view.PatientCreateEditPanel;

import javax.swing.*;


public class PatientListController {

    public PatientListController() {}


    public void newPatient() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            JFrame frame = new JFrame("Patient*in anlegen");
            frame.setSize(400, 300);
            PatientCreateEditPanel patientCreateEditPanel = new PatientCreateEditPanel(frame);
            frame.add(patientCreateEditPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);

            tx.commit();
        }
    }

    public void editPatient(PatientModel patientToEdit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            JFrame frame = new JFrame("Patienteninformation bearbeiten");
            frame.setSize(400, 300);
            PatientCreateEditPanel patientCreateEditPanel = new PatientCreateEditPanel(frame, patientToEdit);
            frame.add(patientCreateEditPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);

            tx.commit();
        }
    }

    public boolean deletePatient(PatientModel patientToDelete) {
        int choice = JOptionPane.showConfirmDialog(null, "Sind Sie sicher, dass Sie diese Patienteninformation löschen möchten?", "Bestätigung des Löschvorgangs", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction tx = session.beginTransaction();
                try {
                    session.delete(patientToDelete);
                    tx.commit();
                    JOptionPane.showMessageDialog(null, "Patienteninformation erfolgreich gelöscht!", "Gelöscht!", JOptionPane.INFORMATION_MESSAGE);
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