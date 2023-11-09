import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import mvc.model.PatientModel;
import java.time.LocalDate;

public class PatientModelTest {

    @Test
    public void testPatientModelGettersAndSetters() {
        PatientModel patient = new PatientModel("John", "Doe", LocalDate.of(1990, 1, 1), "Male");

        assertEquals("John", patient.getFirstName());
        assertEquals("Doe", patient.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), patient.getDateOfBirth());
        assertEquals("Male", patient.getGender());

        patient.setFirstName("Alice");
        assertEquals("Alice", patient.getFirstName());

        patient.setLastName("Smith");
        assertEquals("Smith", patient.getLastName());

        patient.setDateOfBirth(LocalDate.of(2000, 5, 5));
        assertEquals(LocalDate.of(2000, 5, 5), patient.getDateOfBirth());

        patient.setGender("Female");
        assertEquals("Female", patient.getGender());
    }
}