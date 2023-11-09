import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import mvc.model.MedicalRecordModel;
import mvc.model.MedicalRecordType;
import mvc.model.PatientModel;

public class MedicalRecordModelTest {

    @Test
    public void testMedicalRecordModelGettersAndSetters() {
        PatientModel patient = new PatientModel("John", "Doe", LocalDate.of(1990, 1, 1), "Male");

        MedicalRecordModel medicalRecord = new MedicalRecordModel(
                LocalDate.of(2023, 11, 6),
                MedicalRecordType.DIAGNOSE,
                "Description",
                patient
        );

        assertEquals(LocalDate.of(2023, 11, 6), medicalRecord.getDate());
        assertEquals(MedicalRecordType.DIAGNOSE, medicalRecord.getType());
        assertEquals("Description", medicalRecord.getDescription());

        LocalDate newDate = LocalDate.of(2023, 11, 7);
        medicalRecord.setDate(newDate);
        assertEquals(newDate, medicalRecord.getDate());

        MedicalRecordType newType = MedicalRecordType.SCHEIN;
        medicalRecord.setType(newType);
        assertEquals(newType, medicalRecord.getType());

        String newDescription = "Updated description";
        medicalRecord.setDescription(newDescription);
        assertEquals(newDescription, medicalRecord.getDescription());
    }
}
