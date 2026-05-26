package hospital.model.request;

public class DepartmentUpdateRequest {

    private final String name;

    public DepartmentUpdateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
