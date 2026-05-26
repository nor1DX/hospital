package hospital.repository;

import hospital.model.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    Patient save(Patient patient);

    Optional<Patient> findById(Long id);

    List<Patient> findAll();

    List<Patient> findByDepartmentId(Long departmentId);

    void deleteById(Long id);

    boolean existsById(Long id);
}
