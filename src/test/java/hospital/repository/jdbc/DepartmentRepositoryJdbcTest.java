package hospital.repository.jdbc;

import hospital.config.DatabaseConfig;
import hospital.config.DatabaseInitializer;
import hospital.model.entity.Department;
import hospital.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DepartmentRepositoryJdbcTest {

    private DepartmentRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseInitializer.initialize();
        cleanDatabase();
        repository = new DepartmentRepositoryJdbc();
    }

    private void cleanDatabase() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM patients");
            stmt.execute("DELETE FROM departments");
        }
    }

    @Test
    void save_assignsIdToNewDepartment() {
        Department saved = repository.save(new Department(null, "Cardiology", 0));
        assertNotNull(saved.getId());
        assertEquals("Cardiology", saved.getName());
        assertEquals(0, saved.getPatientCount());
    }

    @Test
    void save_updatesExistingDepartment() {
        Department saved = repository.save(new Department(null, "Cardiology", 0));
        saved.setName("Neurology");
        repository.save(saved);

        assertEquals("Neurology", repository.findById(saved.getId()).get().getName());
    }

    @Test
    void findById_returnsEmptyWhenNotFound() {
        assertTrue(repository.findById(999L).isEmpty());
    }

    @Test
    void findAll_returnsAllDepartments() {
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
