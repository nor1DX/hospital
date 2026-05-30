package hospital.component.exception;

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(Long id) {
        super("Department not found: id=" + id);
    }
}
