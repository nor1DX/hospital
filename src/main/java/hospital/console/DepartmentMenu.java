package hospital.console;

import hospital.controller.console.DepartmentController;
import hospital.model.dto.DepartmentDto;
import hospital.model.dto.PatientDto;

import java.util.List;
import java.util.Scanner;

public class DepartmentMenu {

    private final DepartmentController controller;
    private final Scanner scanner;

    public DepartmentMenu(DepartmentController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Departments ===");
            System.out.println("1. List departments");
            System.out.println("2. Add department");
            System.out.println("3. Edit department");
            System.out.println("4. Delete department");
            System.out.println("5. View patients in department");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> listDepartments();
                case "2" -> addDepartment();
                case "3" -> editDepartment();
                case "4" -> deleteDepartment();
                case "5" -> showDepartmentPatients();
                case "0" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listDepartments() {
        List<DepartmentDto> departments = controller.findAll();
        if (departments.isEmpty()) {
            System.out.println("No departments found.");
            return;
        }
        System.out.println("\nID  | Name                         | Patients");
        System.out.println("----|------------------------------|----------");
        for (DepartmentDto d : departments) {
            System.out.printf("%-4d| %-29s| %d%n", d.getId(), d.getName(), d.getPatientCount());
        }
    }

    private void addDepartment() {
        System.out.print("Department name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        DepartmentDto created = controller.create(name);
        System.out.println("Department added: [" + created.getId() + "] " + created.getName());
    }

    private void editDepartment() {
        Long id = readId("Department ID: ");
        if (id == null) {
            return;
        }
        try {
            DepartmentDto current = controller.findById(id);
            System.out.println("Current name: " + current.getName());
            System.out.print("New name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }
            controller.update(id, name);
            System.out.println("Department updated.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteDepartment() {
        Long id = readId("Department ID: ");
        if (id == null) {
            return;
        }
        try {
            controller.delete(id);
            System.out.println("Department deleted.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showDepartmentPatients() {
        Long id = readId("Department ID: ");
        if (id == null) {
            return;
        }
        try {
            DepartmentDto department = controller.findById(id);
            List<PatientDto> patients = controller.getPatients(id);
            System.out.println("\nDepartment: " + department.getName()
                    + " (patients: " + department.getPatientCount() + ")");
            if (patients.isEmpty()) {
                System.out.println("No patients.");
                return;
            }
            System.out.println("ID  | Full name                    | Age     | Gender");
            System.out.println("----|------------------------------|---------|--------");
            for (PatientDto p : patients) {
                System.out.printf("%-4d| %-29s| %-8d| %s%n",
                        p.getId(), p.getFullName(), p.getAge(), p.getGender().getDisplayName());
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Long readId(String prompt) {
        System.out.print(prompt);
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid ID.");
            return null;
        }
    }
}
