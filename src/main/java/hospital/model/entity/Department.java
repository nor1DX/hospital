package hospital.model.entity;

public class Department {

    private Long id;
    private String name;
    private int patientCount;

    public Department(Long id, String name, int patientCount) {
        this.id = id;
        this.name = name;
        this.patientCount = patientCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }
}
