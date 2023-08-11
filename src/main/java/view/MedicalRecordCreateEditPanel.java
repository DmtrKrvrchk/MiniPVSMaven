package view;

import model.MedicalRecordModel;
import model.MedicalRecordTableModel;
import model.MedicalRecordType;
import model.PatientModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class MedicalRecordCreateEditPanel extends JPanel {
    private final PatientModel patient;
    private final JFrame parent;
    private JTextField dateField;
    private JComboBox<MedicalRecordType> typeComboBox;
    private JTextField descriptionField;
    private MedicalRecordModel medicalRecord;


    public MedicalRecordCreateEditPanel(JFrame parent, PatientModel patient) {
        this.parent = parent;
        this.patient = patient;
        initComponents();
    }

    public MedicalRecordCreateEditPanel(JFrame parent, MedicalRecordModel medicalRecord, PatientModel patient){
        this.medicalRecord = medicalRecord;
        this.parent = parent;
        this.patient= patient;
        initComponents();
        initValues();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Datum:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        dateField = new JTextField(today.format(formatter));
        inputPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Typ:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        typeComboBox = new JComboBox<>(MedicalRecordType.values());
        inputPanel.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Beschreibung:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        descriptionField = new JTextField();
        inputPanel.add(descriptionField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1,4,5,5));
        JButton saveButton = new JButton("Speichern");
        JButton cancelButton = new JButton("Löschen");

        int marginSize = 10;
        int borderThickness = 1;
        Border emptyBorder = BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize);
        Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, borderThickness);
        Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);

        saveButton.setBorder(compoundBorder);
        cancelButton.setBorder(compoundBorder);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(e -> {
            if (medicalRecord == null){
                saveMedicalRecord();
            } else {
                editMedicalRecord(medicalRecord);
            }
        });
        cancelButton.addActionListener(e -> parent.dispose());

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);
    }

    public void saveMedicalRecord() {
        if (validateFields()) {
            LocalDate date;
            try {
                date = LocalDate.parse(dateField.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Ungültiges Datum. Bitte verwenden Sie das Format TT-MM-JJJJ.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MedicalRecordType type = (MedicalRecordType) typeComboBox.getSelectedItem();
            String description = descriptionField.getText();

            MedicalRecordModel newMedicalRecord = new MedicalRecordModel(date, type, description, patient);
            MedicalRecordTableModel.getInstance(patient).addMedicalRecord(newMedicalRecord);
            parent.dispose();
        }
    }

    private void editMedicalRecord(MedicalRecordModel medicalRecordToEdit) {
        if (validateFields()) {
            LocalDate date;
            try {
                date = LocalDate.parse(dateField.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Ungültiges Datum. Bitte verwenden Sie das Format TT-MM-JJJJ.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MedicalRecordType type = (MedicalRecordType) typeComboBox.getSelectedItem();
            String description = descriptionField.getText();

            medicalRecordToEdit.setDate(date);
            medicalRecordToEdit.setType(type);
            medicalRecordToEdit.setDescription(description);

            MedicalRecordTableModel.getInstance(patient).fireTableDataChanged();
            parent.dispose();
        }
    }

    private void initValues() {
        if (medicalRecord != null) {
            dateField.setText(medicalRecord.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            typeComboBox.setSelectedItem(medicalRecord.getType());
            descriptionField.setText(medicalRecord.getDescription());
        }
    }

    private boolean validateFields() {
        String dateString = dateField.getText();
        String description = descriptionField.getText();

        if (dateString.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();

            if (year < LocalDate.now().getYear() || year > LocalDate.now().getYear()) {
                JOptionPane.showMessageDialog(this, "Akteneinträge sollen ausschließlich für das laufende Jahr erstellt werden und sie können nicht in der Zukunft liegen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (year == LocalDate.now().getYear()) {
                if (month > LocalDate.now().getMonthValue()) {
                    JOptionPane.showMessageDialog(this, "Ungültiges Datum. Das Datum kann nicht in der Zukunft liegen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else if (month == LocalDate.now().getMonthValue()) {
                    if (day > LocalDate.now().getDayOfMonth()) {
                        JOptionPane.showMessageDialog(this, "Ungültiges Datum. Das Datum kann nicht in der Zukunft liegen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Ungültiges Datum. Bitte verwenden Sie das Format TT-MM-JJJJ.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}