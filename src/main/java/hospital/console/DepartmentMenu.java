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
            System.out.println("\n=== Отделения ===");
            System.out.println("1. Список отделений");
            System.out.println("2. Добавить отделение");
            System.out.println("3. Редактировать отделение");
            System.out.println("4. Удалить отделение");
            System.out.println("5. Пациенты отделения");
            System.out.println("0. Назад");
            System.out.print("Выберите: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> listDepartments();
                case "2" -> addDepartment();
                case "3" -> editDepartment();
                case "4" -> deleteDepartment();
                case "5" -> showDepartmentPatients();
                case "0" -> running = false;
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private void listDepartments() {
        List<DepartmentDto> departments = controller.findAll();
        if (departments.isEmpty()) {
            System.out.println("Отделения не найдены.");
            return;
        }
        System.out.println("\nID  | Название                     | Пациентов");
        System.out.println("----|------------------------------|----------");
        for (DepartmentDto d : departments) {
            System.out.printf("%-4d| %-29s| %d%n", d.getId(), d.getName(), d.getPatientCount());
        }
    }

    private void addDepartment() {
        System.out.print("Название отделения: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Название не может быть пустым.");
            return;
        }
        DepartmentDto created = controller.create(name);
        System.out.println("Отделение добавлено: [" + created.getId() + "] " + created.getName());
    }

    private void editDepartment() {
        Long id = readId("ID отделения: ");
        if (id == null) {
            return;
        }
        try {
            DepartmentDto current = controller.findById(id);
            System.out.println("Текущее название: " + current.getName());
            System.out.print("Новое название: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Название не может быть пустым.");
                return;
            }
            controller.update(id, name);
            System.out.println("Отделение обновлено.");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void deleteDepartment() {
        Long id = readId("ID отделения: ");
        if (id == null) {
            return;
        }
        try {
            controller.delete(id);
            System.out.println("Отделение удалено.");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showDepartmentPatients() {
        Long id = readId("ID отделения: ");
        if (id == null) {
            return;
        }
        try {
            DepartmentDto department = controller.findById(id);
            List<PatientDto> patients = controller.getPatients(id);
            System.out.println("\nОтделение: " + department.getName()
                    + " (пациентов: " + department.getPatientCount() + ")");
            if (patients.isEmpty()) {
                System.out.println("Пациентов нет.");
                return;
            }
            System.out.println("ID  | ФИО                          | Возраст | Пол");
            System.out.println("----|------------------------------|---------|--------");
            for (PatientDto p : patients) {
                System.out.printf("%-4d| %-29s| %-8d| %s%n",
                        p.getId(), p.getFullName(), p.getAge(), p.getGender().getDisplayName());
            }
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private Long readId(String prompt) {
        System.out.print(prompt);
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Введите корректный ID.");
            return null;
        }
    }
}
