package mvc.view;


import mvc.model.PatientModel;

import javax.swing.*;


public class MainMenuView extends JFrame {
    private JTabbedPane tabbedPane;


    public MainMenuView() { initComponents(); }


    public void initComponents() {
        setTitle("MiniPVS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 525);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        PatientListView patientListView = new PatientListView(this);
        tabbedPane.addTab("Patientenliste", patientListView);

        MedicalRecordListView medicalRecordListView = new MedicalRecordListView();
        tabbedPane.addTab("Patientenakte", medicalRecordListView);
        tabbedPane.setEnabledAt(1, false);

        add(tabbedPane);
    }

    public void updatePatientenakteTab(PatientModel patient) {
        if (patient != null) {
            tabbedPane.removeTabAt(1);
            tabbedPane.addTab("Patientenakte", new MedicalRecordListView(patient));
            tabbedPane.setEnabledAt(1, true);
        } else {
            tabbedPane.setEnabledAt(1, false);
        }
    }

    public void switchToMedicalRecordTab(PatientModel selectedPatient) {
        int tabCount = tabbedPane.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            if (tabbedPane.getTitleAt(i).equals("Patientenliste")) {
                tabbedPane.setSelectedIndex(i);
            } else if (tabbedPane.getTitleAt(i).equals("Patientenakte")) {
                tabbedPane.setEnabledAt(i, true);
                tabbedPane.setSelectedIndex(i);
                MedicalRecordListView medicalRecordView = (MedicalRecordListView) tabbedPane.getComponentAt(i);
                medicalRecordView.showMedicalRecordsForPatient(selectedPatient);
            }
        }
    }
}