package hospital;

import hospital.config.ApplicationFactory;

public class Main {

    public static void main(String[] args) throws Exception {
        new ApplicationFactory().createWebApp().run();
    }
}
