package model;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class PatientTableModel extends AbstractTableModel {
    private final ArrayList<PatientModel> patients;
    private final String[] columnNames = {"Name", "Vorname", "Geburtsdatum", "Geschlecht"};
    private static PatientTableModel instance;


    public PatientTableModel() { this.patients = new ArrayList<>(); }


    public static PatientTableModel getInstance() {
        if (instance == null) { instance = new PatientTableModel(); }
        return instance;
    }


    public void addPatient(PatientModel patientToAdd) {
        patients.add(patientToAdd);
        fireTableRowsInserted(patients.size(), patients.size());
    }

    public PatientModel getPatientAt(int row) {
        return patients.get(row);
    }
    public void removePatientAt(int row) {
        patients.remove(row);
        fireTableRowsDeleted(row, row);
    }

    @Override
    public int getRowCount() {
        return patients.size();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PatientModel patient = patients.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return patient.getLastName();
            }
            case 1 -> {
                return patient.getFirstName();
            }
            case 2 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return patient.getDateOfBirth().format(formatter);
            }
            case 3 -> {
                return patient.getGender();
            }
            default -> {
                return null;
            }
        }
    }
}