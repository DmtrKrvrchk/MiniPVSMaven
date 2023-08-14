package mvc.view;

import mvc.controller.PatientListController;
import mvc.model.PatientModel;
import mvc.model.PatientTableModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class PatientListView extends JPanel {
    private final PatientListController patientListController;
    private final JTable patientTable;
    private final MainMenuView mainMenuView;
    private JButton newPatientButton;
    private JButton editPatientButton;
    private JButton medicalRecordButton;
    private JButton deletePatientButton;


    public PatientListView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
        patientListController = new PatientListController();
        patientTable = new JTable(PatientTableModel.getInstance());
        initComponents();
        initActionListener();
    }


    private void initActionListener() {
        patientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRowCount = patientTable.getSelectedRowCount();
                boolean singlePatientSelected = selectedRowCount == 1;
                editPatientButton.setEnabled(singlePatientSelected);
                medicalRecordButton.setEnabled(singlePatientSelected);
                deletePatientButton.setEnabled(singlePatientSelected);
            }
        });

        newPatientButton.addActionListener(e -> {
            patientListController.newPatient();
            patientTable.updateUI();
        });

        editPatientButton.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow >= 0) {
                PatientModel selectedPatient = PatientTableModel.getInstance().getPatientAt(selectedRow);
                patientListController.editPatient(selectedPatient);
            }
        });

        deletePatientButton.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow >= 0) {
                PatientModel selectedPatient = PatientTableModel.getInstance().getPatientAt(selectedRow);
                if (patientListController.deletePatient(selectedPatient)) {
                    PatientTableModel.getInstance().removePatientAt(selectedRow);
                    mainMenuView.updatePatientenakteTab(null);
                }
            }
        });

        medicalRecordButton.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow >= 0) {
                PatientModel selectedPatient = PatientTableModel.getInstance().getPatientAt(selectedRow);
                new MedicalRecordListView(selectedPatient).setVisible(true);
                mainMenuView.updatePatientenakteTab(selectedPatient);
                mainMenuView.switchToMedicalRecordTab(selectedPatient);
            }
        });
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(patientTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        newPatientButton = new JButton("Patient*in anlegen");
        editPatientButton = new JButton("Patient*in bearbeiten");
        medicalRecordButton = new JButton("Patientenakte öffnen");
        deletePatientButton = new JButton("Patient*in löschen");

        editPatientButton.setEnabled(false);
        medicalRecordButton.setEnabled(false);
        deletePatientButton.setEnabled(false);

        int marginSize = 10;
        int borderThickness = 1;
        Border emptyBorder = BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize);
        Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, borderThickness);
        Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);
        newPatientButton.setBorder(compoundBorder);
        editPatientButton.setBorder(compoundBorder);
        medicalRecordButton.setBorder(compoundBorder);
        deletePatientButton.setBorder(compoundBorder);

        JPanel buttonContainer = new JPanel(new GridLayout(4, 1));
        buttonContainer.add(newPatientButton);
        buttonContainer.add(editPatientButton);
        buttonContainer.add(medicalRecordButton);
        buttonContainer.add(deletePatientButton);

        buttonPanel.add(buttonContainer, BorderLayout.NORTH);

        mainPanel.add(buttonPanel, BorderLayout.EAST);
        add(mainPanel);
    }
}