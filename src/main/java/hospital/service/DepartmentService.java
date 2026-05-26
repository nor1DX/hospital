package hospital.service;

import hospital.model.dto.DepartmentDto;
import hospital.model.request.DepartmentCreateRequest;
import hospital.model.request.DepartmentUpdateRequest;

import java.util.List;

public interface DepartmentService {

    DepartmentDto create(DepartmentCreateRequest request);

    DepartmentDto findById(Long id);

    List<DepartmentDto> findAll();

    DepartmentDto update(Long id, DepartmentUpdateRequest request);

    void delete(Long id);
}
