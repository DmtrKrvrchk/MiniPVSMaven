package patientModelTests;

import mvc.model.Gender;
import mvc.model.PatientModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Info")
public class PatientInfoTest {

    private static PatientModel patient;

    @BeforeEach
    public void setUp() {
        patient = new PatientModel("Michael", "Scott", LocalDate.of(1965, 8, 15), Gender.MALE);
    }

    @Test
    public void testGetDateOfBirth() {
        assertEquals(LocalDate.of(1965, 8, 15), patient.getDateOfBirth());
    }

    @Test
    public void testSetDateOfBirth() {
        LocalDate newDateOfBirth = LocalDate.of(1995, 5, 10);
        patient.setDateOfBirth(newDateOfBirth);
        assertEquals(newDateOfBirth, patient.getDateOfBirth());
    }

    @Test
    public void testGetGender() {
        assertEquals(Gender.MALE, patient.getGender());
    }

    @Test
    public void testSetGender() {
        patient.setGender(Gender.FEMALE);
        assertEquals(Gender.FEMALE, patient.getGender());
    }
}