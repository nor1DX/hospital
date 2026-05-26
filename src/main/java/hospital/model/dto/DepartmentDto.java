package hospital.model.dto;

public class DepartmentDto {

    private final Long id;
    private final String name;
    private final int patientCount;

    public DepartmentDto(Long id, String name, int patientCount) {
        this.id = id;
        this.name = name;
        this.patientCount = patientCount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPatientCount() {
        return patientCount;
    }
}
