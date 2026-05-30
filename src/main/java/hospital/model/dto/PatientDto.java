package hospital.model.dto;

import hospital.model.entity.Gender;

public class PatientDto {

    private final Long id;
    private final String fullName;
    private final int age;
    private final Gender gender;
    private final Long departmentId;
    private final String departmentName;

    public PatientDto(Long id, String fullName, int age, Gender gender,
            Long departmentId, String departmentName) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
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

    public String getDepartmentName() {
        return departmentName;
    }
}
