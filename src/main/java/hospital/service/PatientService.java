package hospital.service;

import hospital.model.dto.PatientDto;
import hospital.model.request.PatientCreateRequest;
import hospital.model.request.PatientUpdateRequest;

import java.util.List;

public interface PatientService {

    PatientDto create(PatientCreateRequest request);

    PatientDto findById(Long id);

    List<PatientDto> findAll();

    List<PatientDto> findByDepartmentId(Long departmentId);

    PatientDto update(Long id, PatientUpdateRequest request);

    void delete(Long id);
}
