package hospital.web;

import hospital.controller.console.DepartmentController;
import hospital.controller.console.PatientController;
import hospital.web.servlet.DepartmentServlet;
import hospital.web.servlet.PatientServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class WebApp {

    private static final int PORT = 8080;

    private final DepartmentController departmentController;
    private final PatientController patientController;

    public WebApp(DepartmentController departmentController, PatientController patientController) {
        this.departmentController = departmentController;
        this.patientController = patientController;
    }

    public void run() throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(PORT);
        tomcat.getConnector();

        String webappDir = new File("src/main/webapp").getAbsolutePath();
        Context ctx = tomcat.addWebapp("", webappDir);

        Tomcat.addServlet(ctx, "departmentServlet", new DepartmentServlet(departmentController));
        ctx.addServletMappingDecoded("/departments", "departmentServlet");
        ctx.addServletMappingDecoded("/departments/*", "departmentServlet");

        Tomcat.addServlet(ctx, "patientServlet", new PatientServlet(patientController));
        ctx.addServletMappingDecoded("/patients", "patientServlet");
        ctx.addServletMappingDecoded("/patients/*", "patientServlet");

        tomcat.start();
        System.out.println("Server started at http://localhost:" + PORT);
        tomcat.getServer().await();
    }
}
