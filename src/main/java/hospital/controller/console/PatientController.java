package hospital.controller.console;

import hospital.model.dto.DepartmentDto;
import hospital.model.dto.PatientDto;
import hospital.model.entity.Gender;
import hospital.model.request.PatientCreateRequest;
import hospital.model.request.PatientUpdateRequest;
import hospital.service.DepartmentService;
import hospital.service.PatientService;

import java.util.List;

public class PatientController {

    private final PatientService patientService;
    private final DepartmentService departmentService;

    public PatientController(PatientService patientService, DepartmentService departmentService) {
        this.patientService = patientService;
        this.departmentService = departmentService;
    }

    public PatientDto create(String fullName, int age, Gender gender, Long departmentId) {
        return patientService.create(new PatientCreateRequest(fullName, age, gender, departmentId));
    }

    public List<PatientDto> findAll() {
        return patientService.findAll();
    }

    public PatientDto findById(Long id) {
        return patientService.findById(id);
    }

    public PatientDto update(Long id, String fullName, int age, Gender gender, Long departmentId) {
        return patientService.update(id, new PatientUpdateRequest(fullName, age, gender, departmentId));
    }

    public void delete(Long id) {
        patientService.delete(id);
    }

    public List<DepartmentDto> findAllDepartments() {
        return departmentService.findAll();
    }
}
