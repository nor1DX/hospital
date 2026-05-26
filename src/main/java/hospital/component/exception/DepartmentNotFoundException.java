package hospital.component.exception;

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(Long id) {
        super("Отделение не найдено: id=" + id);
    }
}
