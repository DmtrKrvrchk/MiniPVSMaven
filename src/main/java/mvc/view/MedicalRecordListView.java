package mvc.view;


import mvc.controller.MedicalRecordListController;
import mvc.model.MedicalRecordModel;
import mvc.model.MedicalRecordTableModel;
import mvc.model.PatientModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


public class MedicalRecordListView extends JPanel {
    private MedicalRecordListController medicalRecordListController;
    private PatientModel patient;
    private JTable medicalRecordTable;
    private JButton newRecordButton;
    private JButton editRecordButton;
    private JButton deleteRecordButton;


    public MedicalRecordListView() {}
    public MedicalRecordListView(PatientModel patient) {
        this.patient = patient;
        medicalRecordListController = new MedicalRecordListController(patient, this);
        medicalRecordTable = new JTable(MedicalRecordTableModel.getInstance(patient));
        initComponents();
        initActionsListener();
    }


    private void initActionsListener() {
        newRecordButton.addActionListener(e -> {
           medicalRecordListController.newMedicalRecord();
           medicalRecordTable.updateUI();
        });

        medicalRecordTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRowCount = medicalRecordTable.getSelectedRowCount();
            boolean singlePatientSelected = selectedRowCount == 1;
            editRecordButton.setEnabled(singlePatientSelected);
            deleteRecordButton.setEnabled(singlePatientSelected);
        });

        editRecordButton.addActionListener(e -> {
            int selectedRow = medicalRecordTable.getSelectedRow();
            if (selectedRow >= 0) {
                MedicalRecordModel selectedMedicalRecord = MedicalRecordTableModel.getInstance(patient).getMedicalRecordAt(selectedRow);
                medicalRecordListController.editMedicalRecord(selectedMedicalRecord);
            }
        });

        deleteRecordButton.addActionListener(e -> {
            int selectedRow = medicalRecordTable.getSelectedRow();
            if (selectedRow >= 0) {
                MedicalRecordModel selectedMedicalRecord = MedicalRecordTableModel.getInstance(patient).getMedicalRecordAt(selectedRow);
                if (medicalRecordListController.deleteMedicalRecord(selectedMedicalRecord)) {
                    MedicalRecordTableModel.getInstance(patient).removeMedicalRecordAt(selectedRow);
                }
            }
        });

        editRecordButton.setEnabled(false);
        deleteRecordButton.setEnabled(false);
    }


    private void initComponents() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String patientName = patient.getLastName() + ", " + patient.getFirstName();
        String patientDetails = " - " + patient.getDateOfBirth().format(formatter) + " (" + Period.between(patient.getDateOfBirth(), LocalDate.now()).getYears() +" J.), " + patient.getGender();
        String secondaryTitle = patientName + patientDetails;
        JLabel secondaryTitleLabel = new JLabel(secondaryTitle);
        secondaryTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        secondaryTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(secondaryTitleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(medicalRecordTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        newRecordButton = new JButton("Akteneintrag anlegen");
        editRecordButton = new JButton("Akteneintrag bearbeiten");
        deleteRecordButton = new JButton("Akteneintrag löschen");

        int marginSize = 10;
        int borderThickness = 1;
        Border emptyBorder = BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize);
        Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, borderThickness);
        Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);
        newRecordButton.setBorder(compoundBorder);
        editRecordButton.setBorder(compoundBorder);
        deleteRecordButton.setBorder(compoundBorder);

        JPanel buttonContainer = new JPanel(new GridLayout(3, 1));
        buttonContainer.add(newRecordButton);
        buttonContainer.add(editRecordButton);
        buttonContainer.add(deleteRecordButton);

        buttonPanel.add(buttonContainer, BorderLayout.NORTH);

        mainPanel.add(buttonPanel, BorderLayout.EAST);
        add(mainPanel);
    }

    public void showMedicalRecordsForPatient(PatientModel patient) {
        this.patient = patient;
        medicalRecordTable.setModel(MedicalRecordTableModel.getInstance(patient));
        medicalRecordTable.updateUI();
    }

    public void updateContent(){
        medicalRecordTable.setModel(MedicalRecordTableModel.getInstance(patient));
        medicalRecordTable.updateUI();
    }
}