package patientModelTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import mvc.controller.CurrentSession;
import mvc.model.MedicalRecordModel;
import mvc.model.MedicalRecordType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import mvc.controller.MedicalRecordManager;
import mvc.model.Gender;
import mvc.model.PatientModel;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Tag("GetMedicalRecord")
public class GetMedicalRecordsTest {
    @BeforeAll
    public static void setUp() {
        CurrentSession.initDatabase();
    }

    @Test
    public void testGetMedicalRecords() {
        try (MockedStatic<MedicalRecordManager> mockedStatic = mockStatic(MedicalRecordManager.class)) {
            PatientModel patient = new PatientModel("Patient", "von Test", LocalDate.of(2000, 1, 1), Gender.MALE);
            patient.setId(1);

            List<MedicalRecordModel> expectedRecords = Arrays.asList(
                    new MedicalRecordModel(LocalDate.now(), MedicalRecordType.BRIEF, "Description 1", 1),
                    new MedicalRecordModel(LocalDate.now(), MedicalRecordType.DIAGNOSE, "Description 2", 1)
            );

            mockedStatic.when(() -> MedicalRecordManager.getMedicalRecordsForPatient(patient)).thenReturn(expectedRecords);

            List<MedicalRecordModel> actualRecords = patient.getMedicalRecords();

            mockedStatic.verify(() -> MedicalRecordManager.getMedicalRecordsForPatient(patient));

            assertEquals(expectedRecords, actualRecords);
        }
    }
}