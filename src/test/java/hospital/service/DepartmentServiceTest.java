package hospital.service;

import hospital.component.exception.DepartmentNotFoundException;
import hospital.model.dto.DepartmentDto;
import hospital.model.entity.Department;
import hospital.model.request.DepartmentCreateRequest;
import hospital.model.request.DepartmentUpdateRequest;
import hospital.repository.DepartmentRepository;
import hospital.repository.inmemory.DepartmentRepositoryInMemory;
import hospital.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceTest {

    private DepartmentService service;

    @BeforeEach
    void setUp() {
        DepartmentRepository repository = new DepartmentRepositoryInMemory();
        service = new DepartmentServiceImpl(repository);
    }

    @Test
    void create_returnsDepartmentWithId() {
        DepartmentDto result = service.create(new DepartmentCreateRequest("Cardiology"));

        assertNotNull(result.getId());
        assertEquals("Cardiology", result.getName());
        assertEquals(0, result.getPatientCount());
    }

    @Test
    void findById_throwsWhenDepartmentNotFound() {
        assertThrows(DepartmentNotFoundException.class, () -> service.findById(999L));
    }

    @Test
    void findAll_returnsAllDepartments() {
        service.create(new DepartmentCreateRequest("Cardiology"));
        service.create(new DepartmentCreateRequest("Neurology"));

        List<DepartmentDto> all = service.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void update_changesDepartmentName() {
        DepartmentDto created = service.create(new DepartmentCreateRequest("Cardiology"));
        service.update(created.getId(), new DepartmentUpdateRequest("Neurology"));

        DepartmentDto updated = service.findById(created.getId());
        assertEquals("Neurology", updated.getName());
    }

    @Test
    void delete_removesDepartment() {
        DepartmentDto created = service.create(new DepartmentCreateRequest("Cardiology"));
        service.delete(created.getId());

        assertThrows(DepartmentNotFoundException.class, () -> service.findById(created.getId()));
    }

    @Test
    void delete_throwsWhenDepartmentNotFound() {
        assertThrows(DepartmentNotFoundException.class, () -> service.delete(999L));
    }
}
