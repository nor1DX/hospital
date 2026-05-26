package hospital.repository.inmemory;

import hospital.model.entity.Department;
import hospital.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class DepartmentRepositoryInMemory implements DepartmentRepository {

    private final Map<Long, Department> storage = new LinkedHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public Department save(Department department) {
        if (department.getId() == null) {
            department.setId(idSequence.getAndIncrement());
        }
        storage.put(department.getId(), department);
        return department;
    }

    @Override
    public Optional<Department> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(storage.values());
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
