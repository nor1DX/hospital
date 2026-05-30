package hospital.model.request;

import hospital.model.entity.Gender;

public class PatientUpdateRequest {

    private final String fullName;
    private final int age;
    private final Gender gender;
    private final Long departmentId;

    public PatientUpdateRequest(String fullName, int age, Gender gender, Long departmentId) {
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.departmentId = departmentId;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public Long getDepartmentId() {
        return departmentId;
    }
}
