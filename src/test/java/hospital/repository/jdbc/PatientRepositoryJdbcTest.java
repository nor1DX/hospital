package hospital.repository.jdbc;

import hospital.config.DatabaseConfig;
import hospital.config.DatabaseInitializer;
import hospital.model.entity.Department;
import hospital.model.entity.Gender;
import hospital.model.entity.Patient;
import hospital.repository.DepartmentRepository;
import hospital.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatientRepositoryJdbcTest {

    private PatientRepository patientRepository;
    private Long departmentId;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseInitializer.initialize();
        cleanDatabase();
        DepartmentRepository departmentRepository = new DepartmentRepositoryJdbc();
        patientRepository = new PatientRepositoryJdbc();
        departmentId = departmentRepository.save(new Department(null, "Cardiology", 0)).getId();
    }

    private void cleanDatabase() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM patients");
            stmt.execute("DELETE FROM departments");
        }
    }

    @Test
    void save_assignsIdToNewPatient() {
        Patient saved = patientRepository.save(
                new Patient(null, "John Doe", 30, Gender.MALE, departmentId));
        assertNotNull(saved.getId());
    }

    @Test
    void save_updatesExistingPatient() {
        Patient saved = patientRepository.save(
                new Patient(null, "John Doe", 30, Gender.MALE, departmentId));
        saved.setFullName("Jane Doe");
        saved.setGender(Gender.FEMALE);
        patientRepository.save(saved);

        Patient updated = patientRepository.findById(saved.getId()).get();
        assertEquals("Jane Doe", updated.getFullName());
        assertEquals(Gender.FEMALE, updated.getGender());
    }

    @Test
    void findByDepartmentId_returnsOnlyMatchingPatients() {
        patientRepository.save(new Patient(null, "John Doe", 30, Gender.MALE, departmentId));
        patientRepository.save(new Patient(null, "Jane Doe", 25, Gender.FEMALE, departmentId));

        List<Patient> result = patientRepository.findByDepartmentId(departmentId);
        assertEquals(2, result.size());
    }

    @Test
    void deleteById_removesPatient() {
        Patient saved = patientRepository.save(
                new Patient(null, "John Doe", 30, Gender.MALE, departmentId));
        patientRepository.deleteById(saved.getId());

        assertTrue(patientRepository.findById(saved.getId()).isEmpty());
    }

    @Test
    void findAll_returnsAllPatients() {
        patientRepository.save(new Patient(null, "John Doe", 30, Gender.MALE, departmentId));
        patientRepository.save(new Patient(null, "Jane Doe", 25, Gender.FEMALE, departmentId));

        assertEquals(2, patientRepository.findAll().size());
    }

    @Test
    void existsById_returnsTrueForExisting() {
        Patient saved = patientRepository.save(
                new Patient(null, "John Doe", 30, Gender.MALE, departmentId));
        assertTrue(patientRepository.existsById(saved.getId()));
    }
}
