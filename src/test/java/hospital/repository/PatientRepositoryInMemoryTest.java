package hospital.repository;

import hospital.model.entity.Gender;
import hospital.model.entity.Patient;
import hospital.repository.inmemory.PatientRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientRepositoryInMemoryTest {

    private PatientRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PatientRepositoryInMemory();
    }

    @Test
    void save_assignsIdToNewPatient() {
        Patient patient = new Patient(null, "John Doe", 30, Gender.MALE, 1L);
        Patient saved = repository.save(patient);
        assertNotNull(saved.getId());
    }

    @Test
    void findByDepartmentId_returnsOnlyMatchingPatients() {
        repository.save(new Patient(null, "John Doe", 30, Gender.MALE, 1L));
        repository.save(new Patient(null, "Jane Doe", 25, Gender.FEMALE, 1L));
        repository.save(new Patient(null, "Bob Smith", 40, Gender.MALE, 2L));

        List<Patient> result = repository.findByDepartmentId(1L);
        assertEquals(2, result.size());
    }

    @Test
    void deleteById_removesPatient() {
        Patient saved = repository.save(new Patient(null, "John Doe", 30, Gender.MALE, 1L));
        repository.deleteById(saved.getId());

        assertTrue(repository.findById(saved.getId()).isEmpty());
    }

    @Test
    void findAll_returnsAllPatients() {
        repository.save(new Patient(null, "John Doe", 30, Gender.MALE, 1L));
        repository.save(new Patient(null, "Jane Doe", 25, Gender.FEMALE, 2L));

        assertEquals(2, repository.findAll().size());
    }
}
