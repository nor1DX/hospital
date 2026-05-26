package hospital.component.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(Long id) {
        super("Пациент не найден: id=" + id);
    }
}
