package hospital.web.servlet;

import hospital.controller.console.PatientController;
import hospital.model.dto.DepartmentDto;
import hospital.model.dto.PatientDto;
import hospital.model.entity.Gender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PatientServlet extends HttpServlet {

    private final PatientController patientController;

    public PatientServlet(PatientController patientController) {
        this.patientController = patientController;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<PatientDto> patients = patientController.findAll();
            req.setAttribute("patients", patients);
            req.getRequestDispatcher("/WEB-INF/jsp/patient/list.jsp").forward(req, resp);
        } else if (pathInfo.equals("/new")) {
            req.setAttribute("departments", patientController.findAllDepartments());
            req.setAttribute("genders", Gender.values());
            req.getRequestDispatcher("/WEB-INF/jsp/patient/form.jsp").forward(req, resp);
        } else {
            String[] parts = pathInfo.split("/");
            Long id;
            try {
                id = Long.parseLong(parts[1]);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (parts.length >= 3 && "edit".equals(parts[2])) {
                req.setAttribute("patient", patientController.findById(id));
                req.setAttribute("departments", patientController.findAllDepartments());
                req.setAttribute("genders", Gender.values());
                req.getRequestDispatcher("/WEB-INF/jsp/patient/form.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            String fullName = req.getParameter("fullName");
            int age = Integer.parseInt(req.getParameter("age"));
            Gender gender = Gender.valueOf(req.getParameter("gender"));
            Long departmentId = Long.parseLong(req.getParameter("departmentId"));
            patientController.create(fullName, age, gender, departmentId);
            resp.sendRedirect(req.getContextPath() + "/patients");
        } else {
            String[] parts = pathInfo.split("/");
            Long id;
            try {
                id = Long.parseLong(parts[1]);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (parts.length == 2) {
                String fullName = req.getParameter("fullName");
                int age = Integer.parseInt(req.getParameter("age"));
                Gender gender = Gender.valueOf(req.getParameter("gender"));
                Long departmentId = Long.parseLong(req.getParameter("departmentId"));
                patientController.update(id, fullName, age, gender, departmentId);
                resp.sendRedirect(req.getContextPath() + "/patients");
            } else if ("delete".equals(parts[2])) {
                patientController.delete(id);
                resp.sendRedirect(req.getContextPath() + "/patients");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
