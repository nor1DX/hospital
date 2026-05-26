package hospital.service;

import hospital.component.exception.DepartmentNotFoundException;
import hospital.component.exception.PatientNotFoundException;
import hospital.model.dto.PatientDto;
import hospital.model.entity.Department;
import hospital.model.entity.Patient;
import hospital.model.request.PatientCreateRequest;
import hospital.model.request.PatientUpdateRequest;
import hospital.repository.DepartmentRepository;
import hospital.repository.PatientRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;

    public PatientServiceImpl(PatientRepository patientRepository,
            DepartmentRepository departmentRepository) {
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public PatientDto create(PatientCreateRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(request.getDepartmentId()));

        Patient patient = new Patient(null, request.getFullName(), request.getAge(),
                request.getGender(), request.getDepartmentId());
        Patient saved = patientRepository.save(patient);

        department.setPatientCount(department.getPatientCount() + 1);
        departmentRepository.save(department);

        return toDto(saved, department.getName());
    }

    @Override
    public PatientDto findById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        return toDto(patient, resolveDepartmentName(patient.getDepartmentId()));
    }

    @Override
    public List<PatientDto> findAll() {
        return patientRepository.findAll().stream()
                .map(p -> toDto(p, resolveDepartmentName(p.getDepartmentId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> findByDepartmentId(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new DepartmentNotFoundException(departmentId);
        }
        String departmentName = resolveDepartmentName(departmentId);
        return patientRepository.findByDepartmentId(departmentId).stream()
                .map(p -> toDto(p, departmentName))
                .collect(Collectors.toList());
    }

    @Override
    public PatientDto update(Long id, PatientUpdateRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        Long oldDepartmentId = patient.getDepartmentId();
        Long newDepartmentId = request.getDepartmentId();

        if (!oldDepartmentId.equals(newDepartmentId)) {
            departmentRepository.findById(oldDepartmentId).ifPresent(d -> {
                d.setPatientCount(Math.max(0, d.getPatientCount() - 1));
                departmentRepository.save(d);
            });
            Department newDepartment = departmentRepository.findById(newDepartmentId)
                    .orElseThrow(() -> new DepartmentNotFoundException(newDepartmentId));
            newDepartment.setPatientCount(newDepartment.getPatientCount() + 1);
            departmentRepository.save(newDepartment);
        }

        patient.setFullName(request.getFullName());
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setDepartmentId(newDepartmentId);

        return toDto(patientRepository.save(patient), resolveDepartmentName(newDepartmentId));
    }

    @Override
    public void delete(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        departmentRepository.findById(patient.getDepartmentId()).ifPresent(d -> {
            d.setPatientCount(Math.max(0, d.getPatientCount() - 1));
            departmentRepository.save(d);
        });

        patientRepository.deleteById(id);
    }

    private String resolveDepartmentName(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .map(Department::getName)
                .orElse("—");
    }

    private PatientDto toDto(Patient patient, String departmentName) {
        return new PatientDto(patient.getId(), patient.getFullName(), patient.getAge(),
                patient.getGender(), patient.getDepartmentId(), departmentName);
    }
}
