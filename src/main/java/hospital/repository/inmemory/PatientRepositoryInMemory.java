package hospital.repository.inmemory;

import hospital.model.entity.Patient;
import hospital.repository.PatientRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PatientRepositoryInMemory implements PatientRepository {

    private final Map<Long, Patient> storage = new LinkedHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            patient.setId(idSequence.getAndIncrement());
        }
        storage.put(patient.getId(), patient);
        return patient;
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Patient> findByDepartmentId(Long departmentId) {
        return storage.values().stream()
                .filter(p -> departmentId.equals(p.getDepartmentId()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
