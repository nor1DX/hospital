package hospital.service;

import hospital.component.exception.DepartmentNotFoundException;
import hospital.component.exception.PatientNotFoundException;
import hospital.model.dto.DepartmentDto;
import hospital.model.dto.PatientDto;
import hospital.model.entity.Gender;
import hospital.model.request.DepartmentCreateRequest;
import hospital.model.request.PatientCreateRequest;
import hospital.model.request.PatientUpdateRequest;
import hospital.repository.DepartmentRepository;
import hospital.repository.PatientRepository;
import hospital.repository.inmemory.DepartmentRepositoryInMemory;
import hospital.repository.inmemory.PatientRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientServiceTest {

    private DepartmentService departmentService;
    private PatientService patientService;
    private Long departmentId;

    @BeforeEach
    void setUp() {
        DepartmentRepository departmentRepository = new DepartmentRepositoryInMemory();
        PatientRepository patientRepository = new PatientRepositoryInMemory();
        departmentService = new DepartmentServiceImpl(departmentRepository);
        patientService = new PatientServiceImpl(patientRepository, departmentRepository);

        departmentId = departmentService.create(new DepartmentCreateRequest("Cardiology")).getId();
    }

    @Test
    void create_incrementsDepartmentPatientCount() {
        patientService.create(new PatientCreateRequest("John Doe", 30, Gender.MALE, departmentId));

        DepartmentDto department = departmentService.findById(departmentId);
        assertEquals(1, department.getPatientCount());
    }

    @Test
    void delete_decrementsDepartmentPatientCount() {
        PatientDto patient = patientService.create(
                new PatientCreateRequest("John Doe", 30, Gender.MALE, departmentId));
        patientService.delete(patient.getId());

        DepartmentDto department = departmentService.findById(departmentId);
        assertEquals(0, department.getPatientCount());
    }

    @Test
    void update_changeDepartment_updatesCountsInBothDepartments() {
        Long otherDepartmentId = departmentService
                .create(new DepartmentCreateRequest("Neurology")).getId();

        PatientDto patient = patientService.create(
                new PatientCreateRequest("John Doe", 30, Gender.MALE, departmentId));
        patientService.update(patient.getId(),
                new PatientUpdateRequest("John Doe", 30, Gender.MALE, otherDepartmentId));

        assertEquals(0, departmentService.findById(departmentId).getPatientCount());
        assertEquals(1, departmentService.findById(otherDepartmentId).getPatientCount());
    }

    @Test
    void create_throwsWhenDepartmentNotFound() {
        assertThrows(DepartmentNotFoundException.class,
                () -> patientService.create(
                        new PatientCreateRequest("John Doe", 30, Gender.MALE, 999L)));
    }

    @Test
    void findById_throwsWhenPatientNotFound() {
        assertThrows(PatientNotFoundException.class, () -> patientService.findById(999L));
    }

    @Test
    void delete_throwsWhenPatientNotFound() {
        assertThrows(PatientNotFoundException.class, () -> patientService.delete(999L));
    }
}
