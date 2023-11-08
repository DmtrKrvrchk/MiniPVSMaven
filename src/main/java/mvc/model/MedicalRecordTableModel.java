package mvc.model;


import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class MedicalRecordTableModel extends AbstractTableModel {
    private final List<MedicalRecordModel> medicalRecords;
    private final String[] columnNames = {"Datum", "Typ", "Beschreibung"};


    public MedicalRecordTableModel(PatientModel patient) { this.medicalRecords = patient.getMedicalRecords(); }


    public void addMedicalRecord(MedicalRecordModel medicalRecordToAdd) {
        medicalRecords.add(medicalRecordToAdd);
        fireTableRowsInserted(medicalRecords.size(), medicalRecords.size());
    }

    public MedicalRecordModel getMedicalRecordAt(int row) {
        return medicalRecords.get(row);
    }
    public void removeMedicalRecordAt(int row) {
        medicalRecords.remove(row);
        fireTableRowsDeleted(row, row);
    }

    @Override
    public int getRowCount() {
        return medicalRecords.size();
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
        MedicalRecordModel medicalRecord = medicalRecords.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return medicalRecord.getDate().format(formatter);
            }
            case 1 -> {
                return medicalRecord.getType();
            }
            case 2 -> {
                return medicalRecord.getDescription();
            }
            default -> {
                return null;
            }
        }
    }
}