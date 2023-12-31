package mvc.view;


import mvc.controller.PatientManager;
import mvc.model.Gender;
import mvc.model.PatientModel;
import mvc.model.PatientTableModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class PatientCreateEditPanel extends JPanel {
	private PatientModel patient;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField birthDateField;
	private JComboBox<Gender> genderComboBox;
	private final JFrame parent;
	private final PatientManager patientManager;


	public PatientCreateEditPanel(JFrame parent) {
		this.parent = parent;
		this.patientManager = new PatientManager();
		initComponents();
	}

	public PatientCreateEditPanel(JFrame parent, PatientModel patient) {
		this.patient = patient;
		this.parent = parent;
		this.patientManager = new PatientManager();
		initComponents();
		initValues();
	}


	private void initComponents() {
		this.setLayout(new BorderLayout());
		parent.setLocationRelativeTo(null);

		JPanel inputPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 5, 10);

		gbc.gridx = 0;
		gbc.gridy = 0;
		inputPanel.add(new JLabel("Vorname:"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		firstNameField = new JTextField();
		inputPanel.add(firstNameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		inputPanel.add(new JLabel("Nachname:"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		lastNameField = new JTextField();
		inputPanel.add(lastNameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		inputPanel.add(new JLabel("Geburtsdatum:"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		birthDateField = new JTextField();
		inputPanel.add(birthDateField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		inputPanel.add(new JLabel("Geschlecht:"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		genderComboBox = new JComboBox<>(Gender.values());
		inputPanel.add(genderComboBox, gbc);

		JPanel buttonPanel = new JPanel(new GridLayout(1,4,5,5));
		JButton saveButton = new JButton("Speichern");
		JButton cancelButton = new JButton("Abbrechen");

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
			if (patient == null){
				savePatient();
			} else {
				editPatient(patient);
			}
		});
		cancelButton.addActionListener(e -> parent.dispose());

		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
	}


	private void savePatient() {
		if (validateFields()) {
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			LocalDate birthDate;
			try {
				birthDate = LocalDate.parse(birthDateField.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			} catch (DateTimeParseException e) {
				JOptionPane.showMessageDialog(this, "Ungültiges Geburtsdatum. Bitte verwenden Sie das Format TT.MM.JJJJ.", "Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Gender gender = (Gender) genderComboBox.getSelectedItem();

			PatientModel newPatient = new PatientModel(firstName, lastName, birthDate, gender);
			patientManager.createPatient(newPatient);

			PatientTableModel.getInstance().addPatient(newPatient);
			parent.dispose();
		}
	}

	private void editPatient(PatientModel patientToEdit) {
		if (validateFields()) {
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			LocalDate birthDate;
			try {
				birthDate = LocalDate.parse(birthDateField.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			} catch (DateTimeParseException e) {
				JOptionPane.showMessageDialog(this, "Ungültiges Geburtsdatum. Bitte verwenden Sie das Format TT.MM.JJJJ.", "Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Gender gender = (Gender) genderComboBox.getSelectedItem();

			patientToEdit.setFirstName(firstName);
			patientToEdit.setLastName(lastName);
			patientToEdit.setDateOfBirth(birthDate);
			patientToEdit.setGender(gender);

			patientManager.updatePatient(patientToEdit);
			PatientTableModel.getInstance().fireTableDataChanged();
			parent.dispose();
		}
	}


	private void initValues() {
		if (patient != null) {
			firstNameField.setText(patient.getFirstName());
			lastNameField.setText(patient.getLastName());
			birthDateField.setText(patient.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
			genderComboBox.setSelectedItem(patient.getGender());
		}
	}

	private boolean validateFields() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String birthDateString = birthDateField.getText();

		if (firstName.isEmpty() || lastName.isEmpty() || birthDateString.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			int birthYear = birthDate.getYear();
			int birthMonth = birthDate.getMonthValue();
			int birthDay = birthDate.getDayOfMonth();

			if (birthYear < 1900 || birthYear > LocalDate.now().getYear()) {
				JOptionPane.showMessageDialog(this, "Ungültiges Geburtsdatum. Das Geburtsdatum kann nicht vor 1900 oder in der Zukunft liegen.", "Fehler", JOptionPane.ERROR_MESSAGE);
				return false;
			} else if (birthYear == LocalDate.now().getYear()) {
				if (birthMonth > LocalDate.now().getMonthValue()) {
					JOptionPane.showMessageDialog(this, "Ungültiges Geburtsdatum. Das Geburtsdatum kann nicht in der Zukunft liegen.", "Fehler", JOptionPane.ERROR_MESSAGE);
					return false;
				} else if (birthMonth == LocalDate.now().getMonthValue()) {
					if (birthDay > LocalDate.now().getDayOfMonth()) {
						JOptionPane.showMessageDialog(this, "Ungültiges Geburtsdatum. Das Geburtsdatum kann nicht in der Zukunft liegen.", "Fehler", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			}
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(this, "Ungültiges Geburtsdatum. Bitte verwenden Sie das Format TT.MM.JJJJ.", "Fehler", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}