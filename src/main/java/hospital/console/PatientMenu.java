package hospital.console;

import hospital.controller.console.PatientController;
import hospital.model.dto.DepartmentDto;
import hospital.model.dto.PatientDto;
import hospital.model.entity.Gender;

import java.util.List;
import java.util.Scanner;

public class PatientMenu {

    private final PatientController controller;
    private final Scanner scanner;

    public PatientMenu(PatientController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Patients ===");
            System.out.println("1. List all patients");
            System.out.println("2. Add patient");
            System.out.println("3. Edit patient");
            System.out.println("4. Delete patient");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> listPatients();
                case "2" -> addPatient();
                case "3" -> editPatient();
                case "4" -> deletePatient();
                case "0" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listPatients() {
        List<PatientDto> patients = controller.findAll();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        System.out.println("\nID  | Full name                    | Age     | Gender   | Department");
        System.out.println("----|------------------------------|---------|----------|--------------------");
        for (PatientDto p : patients) {
            System.out.printf("%-4d| %-29s| %-8d| %-9s| %s%n",
                    p.getId(), p.getFullName(), p.getAge(),
                    p.getGender().getDisplayName(), p.getDepartmentName());
        }
    }

    private void addPatient() {
        List<DepartmentDto> departments = controller.findAllDepartments();
        if (departments.isEmpty()) {
            System.out.println("Please create a department first.");
            return;
        }

        System.out.print("Full name: ");
        String fullName = scanner.nextLine().trim();
        if (fullName.isEmpty()) {
            System.out.println("Full name cannot be empty.");
            return;
        }

        Integer age = readInt("Age: ");
        if (age == null) {
            return;
        }

        Gender gender = readGender();
        if (gender == null) {
            return;
        }

        System.out.println("Available departments:");
        for (DepartmentDto d : departments) {
            System.out.println("  [" + d.getId() + "] " + d.getName());
        }
        Long departmentId = readId("Department ID: ");
        if (departmentId == null) {
            return;
        }

        try {
            PatientDto created = controller.create(fullName, age, gender, departmentId);
            System.out.println("Patient added: [" + created.getId() + "] " + created.getFullName());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editPatient() {
        Long id = readId("Patient ID: ");
        if (id == null) {
            return;
        }
        try {
            PatientDto current = controller.findById(id);
            System.out.println("Current: " + current.getFullName()
                    + ", age " + current.getAge()
                    + ", " + current.getGender().getDisplayName()
                    + ", department: " + current.getDepartmentName());

            System.out.print("New full name [" + current.getFullName() + "]: ");
            String fullName = scanner.nextLine().trim();
            if (fullName.isEmpty()) {
                fullName = current.getFullName();
            }

            Integer age = readInt("New age [" + current.getAge() + "]: ");
            if (age == null) {
                return;
            }

            Gender gender = readGender();
            if (gender == null) {
                return;
            }

            List<DepartmentDto> departments = controller.findAllDepartments();
            System.out.println("Available departments:");
            for (DepartmentDto d : departments) {
                System.out.println("  [" + d.getId() + "] " + d.getName());
            }
            Long departmentId = readId("Department ID [" + current.getDepartmentId() + "]: ");
            if (departmentId == null) {
                departmentId = current.getDepartmentId();
            }

            controller.update(id, fullName, age, gender, departmentId);
            System.out.println("Patient updated.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deletePatient() {
        Long id = readId("Patient ID: ");
        if (id == null) {
            return;
        }
        try {
            controller.delete(id);
            System.out.println("Patient deleted.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Gender readGender() {
        System.out.println("Gender: 1. Male  2. Female");
        System.out.print("Choose: ");
        return switch (scanner.nextLine().trim()) {
            case "1" -> Gender.MALE;
            case "2" -> Gender.FEMALE;
            default -> {
                System.out.println("Invalid gender choice.");
                yield null;
            }
        };
    }

    private Long readId(String prompt) {
        System.out.print(prompt);
        try {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                return null;
            }
            return Long.parseLong(line);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid ID.");
            return null;
        }
    }

    private Integer readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return null;
        }
    }
}
