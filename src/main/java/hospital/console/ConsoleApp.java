package hospital.console;

import hospital.controller.console.DepartmentController;
import hospital.controller.console.PatientController;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleApp {

    private enum MainMenuOption {
        DEPARTMENTS("1", "Departments"),
        PATIENTS("2", "Patients"),
        EXIT("0", "Exit");

        private final String key;
        private final String label;

        MainMenuOption(String key, String label) {
            this.key = key;
            this.label = label;
        }

        static Optional<MainMenuOption> fromKey(String key) {
            return Arrays.stream(values())
                    .filter(o -> o.key.equals(key))
                    .findFirst();
        }
    }

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
        System.out.println("=== Hospital Management System ===");
        boolean running = true;
        while (running) {
            System.out.println();
            for (MainMenuOption option : MainMenuOption.values()) {
                System.out.println(option.key + ". " + option.label);
            }
            System.out.print("Choose: ");

            MainMenuOption chosen = MainMenuOption.fromKey(scanner.nextLine().trim()).orElse(null);
            if (chosen == null) {
                System.out.println("Invalid option.");
                continue;
            }

            switch (chosen) {
                case DEPARTMENTS -> departmentMenu.show();
                case PATIENTS -> patientMenu.show();
                case EXIT -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
            }
        }
    }
}
