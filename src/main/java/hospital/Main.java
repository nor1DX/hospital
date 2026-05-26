package hospital;

import hospital.config.ApplicationFactory;
import hospital.console.ConsoleApp;

public class Main {

    public static void main(String[] args) {
        ApplicationFactory factory = new ApplicationFactory();
        ConsoleApp app = new ConsoleApp(
                factory.getDepartmentController(),
                factory.getPatientController()
        );
        app.run();
    }
}
