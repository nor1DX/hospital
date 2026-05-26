package hospital.console;

import hospital.controller.console.DepartmentController;
import hospital.controller.console.PatientController;

import java.util.Scanner;

public class ConsoleApp {

    private final DepartmentMenu departmentMenu;
    private final PatientMenu patientMenu;
    private final Scanner scanner;

    public ConsoleApp(DepartmentController departmentController,
            PatientController patientController) {
        this.scanner = new Scanner(System.in);
        this.departmentMenu = new DepartmentMenu(departmentController, scanner);
        this.patientMenu = new PatientMenu(patientController, scanner);
    }

    public void run() {
        System.out.println("=== Система управления больницей ===");
        boolean running = true;
        while (running) {
            System.out.println("\n1. Отделения");
            System.out.println("2. Пациенты");
            System.out.println("0. Выход");
            System.out.print("Выберите: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> departmentMenu.show();
                case "2" -> patientMenu.show();
                case "0" -> {
                    running = false;
                    System.out.println("До свидания!");
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }
}
