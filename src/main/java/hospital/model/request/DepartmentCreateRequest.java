package hospital.model.request;

public class DepartmentCreateRequest {

    private final String name;

    public DepartmentCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
