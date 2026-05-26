package hospital.config;

import hospital.controller.console.DepartmentController;
import hospital.controller.console.PatientController;
import hospital.repository.DepartmentRepository;
import hospital.repository.PatientRepository;
import hospital.repository.inmemory.DepartmentRepositoryInMemory;
import hospital.repository.inmemory.PatientRepositoryInMemory;
import hospital.service.DepartmentService;
import hospital.service.DepartmentServiceImpl;
import hospital.service.PatientService;
import hospital.service.PatientServiceImpl;

public class ApplicationFactory {

    private static final RepositoryType REPOSITORY_TYPE = RepositoryType.IN_MEMORY;

    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;
    private final DepartmentService departmentService;
    private final PatientService patientService;
    private final DepartmentController departmentController;
    private final PatientController patientController;

    public ApplicationFactory() {
        this.departmentRepository = createDepartmentRepository();
        this.patientRepository = createPatientRepository();
        this.departmentService = new DepartmentServiceImpl(departmentRepository);
        this.patientService = new PatientServiceImpl(patientRepository, departmentRepository);
        this.departmentController = new DepartmentController(departmentService, patientService);
        this.patientController = new PatientController(patientService, departmentService);
    }

    private DepartmentRepository createDepartmentRepository() {
        return switch (REPOSITORY_TYPE) {
            case IN_MEMORY -> new DepartmentRepositoryInMemory();
            case JDBC -> throw new UnsupportedOperationException("JDBC not implemented yet");
        };
    }

    private PatientRepository createPatientRepository() {
        return switch (REPOSITORY_TYPE) {
            case IN_MEMORY -> new PatientRepositoryInMemory();
            case JDBC -> throw new UnsupportedOperationException("JDBC not implemented yet");
        };
    }

    public DepartmentController getDepartmentController() {
        return departmentController;
    }

    public PatientController getPatientController() {
        return patientController;
    }
}
