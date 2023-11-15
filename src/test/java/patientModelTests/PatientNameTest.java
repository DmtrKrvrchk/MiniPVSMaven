package patientModelTests;

import mvc.model.Gender;
import mvc.model.PatientModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Name")
public class PatientNameTest {

    private static PatientModel patient;

    @BeforeEach
    public void setUp() {
        patient = new PatientModel("Michael", "Scott", LocalDate.of(1965, 8, 15), Gender.MALE);
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Michael", patient.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        patient.setFirstName("Jane");

        assertEquals("Jane", patient.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Scott", patient.getLastName());
    }

    @Test
    public void testSetLastName() {
        patient.setLastName("Patientin");

        assertEquals("Patientin", patient.getLastName());
    }
}
