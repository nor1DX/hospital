package hospital.repository;

import hospital.model.entity.Department;
import hospital.repository.inmemory.DepartmentRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentRepositoryInMemoryTest {

    private DepartmentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new DepartmentRepositoryInMemory();
    }

    @Test
    void save_assignsIdToNewDepartment() {
        Department department = new Department(null, "Cardiology", 0);
        Department saved = repository.save(department);
        assertNotNull(saved.getId());
    }

    @Test
    void save_updatesExistingDepartment() {
        Department department = repository.save(new Department(null, "Cardiology", 0));
        department.setName("Neurology");
        repository.save(department);

        assertEquals("Neurology", repository.findById(department.getId()).get().getName());
    }

    @Test
    void findById_returnsEmptyWhenNotFound() {
        Optional<Department> result = repository.findById(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_returnsAllSavedDepartments() {
        repository.save(new Department(null, "Cardiology", 0));
        repository.save(new Department(null, "Neurology", 0));

        List<Department> all = repository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void deleteById_removesDepartment() {
        Department saved = repository.save(new Department(null, "Cardiology", 0));
        repository.deleteById(saved.getId());

        assertFalse(repository.existsById(saved.getId()));
    }

    @Test
    void existsById_returnsTrueForExisting() {
        Department saved = repository.save(new Department(null, "Cardiology", 0));
        assertTrue(repository.existsById(saved.getId()));
    }
}
