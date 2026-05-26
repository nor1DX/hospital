package hospital.model.entity;

public class Patient {

    private Long id;
    private String fullName;
    private int age;
    private Gender gender;
    private Long departmentId;

    public Patient(Long id, String fullName, int age, Gender gender, Long departmentId) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
