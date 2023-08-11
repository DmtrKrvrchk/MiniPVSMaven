package controller;

import model.PatientModel;
import org.hibernate.Session;
import util.HibernateUtil;
import view.PatientCreateEditPanel;

import javax.swing.*;


public class PatientListController {

    public PatientListController() {}


    public void newPatient() {
        JFrame frame = new JFrame("Patient*in anlegen");
        frame.setSize(400, 300);
        PatientCreateEditPanel patientCreateEditPanel = new PatientCreateEditPanel(frame);
        frame.add(patientCreateEditPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void editPatient(PatientModel patientToEdit) {
        JFrame frame = new JFrame("Patienteninformation bearbeiten");
        frame.setSize(400, 300);
        PatientCreateEditPanel patientCreateEditPanel = new PatientCreateEditPanel(frame, patientToEdit);
        frame.add(patientCreateEditPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public boolean deletePatient(PatientModel patientToDelete) {
        int choice = JOptionPane.showConfirmDialog(null, "Sind Sie sicher, dass Sie diese Patienteninformation löschen möchten?", "Bestätigung des Löschvorgangs", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            try {
                session.getTransaction().begin();
                session.delete(patientToDelete);
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
            JOptionPane.showMessageDialog(null, "Patienteninformation erfolgreich gelöscht!", "Gelöscht!", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            return false;
        }
    }
}