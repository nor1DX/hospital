package hospital.service.impl;

import hospital.component.exception.DepartmentNotFoundException;
import hospital.model.dto.DepartmentDto;
import hospital.model.entity.Department;
import hospital.model.request.DepartmentCreateRequest;
import hospital.model.request.DepartmentUpdateRequest;
import hospital.repository.DepartmentRepository;
import hospital.service.DepartmentService;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDto create(DepartmentCreateRequest request) {
        Department department = new Department(null, request.getName(), 0);
        return toDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto findById(Long id) {
        return toDto(departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id)));
    }

    @Override
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto update(Long id, DepartmentUpdateRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        department.setName(request.getName());
        return toDto(departmentRepository.save(department));
    }

    @Override
    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException(id);
        }
        departmentRepository.deleteById(id);
    }

    private DepartmentDto toDto(Department department) {
        return new DepartmentDto(department.getId(), department.getName(), department.getPatientCount());
    }
}
