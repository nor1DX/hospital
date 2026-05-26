package hospital.controller.console;

import hospital.model.dto.DepartmentDto;
import hospital.model.dto.PatientDto;
import hospital.model.request.DepartmentCreateRequest;
import hospital.model.request.DepartmentUpdateRequest;
import hospital.service.DepartmentService;
import hospital.service.PatientService;

import java.util.List;

public class DepartmentController {

    private final DepartmentService departmentService;
    private final PatientService patientService;

    public DepartmentController(DepartmentService departmentService, PatientService patientService) {
        this.departmentService = departmentService;
        this.patientService = patientService;
    }

    public DepartmentDto create(String name) {
        return departmentService.create(new DepartmentCreateRequest(name));
    }

    public List<DepartmentDto> findAll() {
        return departmentService.findAll();
    }

    public DepartmentDto findById(Long id) {
        return departmentService.findById(id);
    }

    public DepartmentDto update(Long id, String name) {
        return departmentService.update(id, new DepartmentUpdateRequest(name));
    }

    public void delete(Long id) {
        departmentService.delete(id);
    }

    public List<PatientDto> getPatients(Long departmentId) {
        return patientService.findByDepartmentId(departmentId);
    }
}
