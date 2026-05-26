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
            System.out.println("\n=== Пациенты ===");
            System.out.println("1. Список всех пациентов");
            System.out.println("2. Добавить пациента");
            System.out.println("3. Редактировать пациента");
            System.out.println("4. Удалить пациента");
            System.out.println("0. Назад");
            System.out.print("Выберите: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> listPatients();
                case "2" -> addPatient();
                case "3" -> editPatient();
                case "4" -> deletePatient();
                case "0" -> running = false;
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private void listPatients() {
        List<PatientDto> patients = controller.findAll();
        if (patients.isEmpty()) {
            System.out.println("Пациенты не найдены.");
            return;
        }
        System.out.println("\nID  | ФИО                          | Возраст | Пол      | Отделение");
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
            System.out.println("Сначала создайте хотя бы одно отделение.");
            return;
        }

        System.out.print("ФИО пациента: ");
        String fullName = scanner.nextLine().trim();
        if (fullName.isEmpty()) {
            System.out.println("ФИО не может быть пустым.");
            return;
        }

        Integer age = readInt("Возраст: ");
        if (age == null) {
            return;
        }

        Gender gender = readGender();
        if (gender == null) {
            return;
        }

        System.out.println("Доступные отделения:");
        for (DepartmentDto d : departments) {
            System.out.println("  [" + d.getId() + "] " + d.getName());
        }
        Long departmentId = readId("ID отделения: ");
        if (departmentId == null) {
            return;
        }

        try {
            PatientDto created = controller.create(fullName, age, gender, departmentId);
            System.out.println("Пациент добавлен: [" + created.getId() + "] " + created.getFullName());
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void editPatient() {
        Long id = readId("ID пациента: ");
        if (id == null) {
            return;
        }
        try {
            PatientDto current = controller.findById(id);
            System.out.println("Текущие данные: " + current.getFullName()
                    + ", " + current.getAge() + " лет, " + current.getGender().getDisplayName()
                    + ", отделение: " + current.getDepartmentName());

            System.out.print("Новое ФИО [" + current.getFullName() + "]: ");
            String fullName = scanner.nextLine().trim();
            if (fullName.isEmpty()) {
                fullName = current.getFullName();
            }

            Integer age = readInt("Новый возраст [" + current.getAge() + "]: ");
            if (age == null) {
                return;
            }

            Gender gender = readGender();
            if (gender == null) {
                return;
            }

            List<DepartmentDto> departments = controller.findAllDepartments();
            System.out.println("Доступные отделения:");
            for (DepartmentDto d : departments) {
                System.out.println("  [" + d.getId() + "] " + d.getName());
            }
            Long departmentId = readId("ID отделения [" + current.getDepartmentId() + "]: ");
            if (departmentId == null) {
                departmentId = current.getDepartmentId();
            }

            controller.update(id, fullName, age, gender, departmentId);
            System.out.println("Пациент обновлён.");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void deletePatient() {
        Long id = readId("ID пациента: ");
        if (id == null) {
            return;
        }
        try {
            controller.delete(id);
            System.out.println("Пациент удалён.");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private Gender readGender() {
        System.out.println("Пол: 1. Мужской  2. Женский");
        System.out.print("Выберите: ");
        return switch (scanner.nextLine().trim()) {
            case "1" -> Gender.MALE;
            case "2" -> Gender.FEMALE;
            default -> {
                System.out.println("Неверный выбор пола.");
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
            System.out.println("Введите корректный ID.");
            return null;
        }
    }

    private Integer readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Введите корректное число.");
            return null;
        }
    }
}
