package hospital.web.servlet;

import hospital.controller.console.DepartmentController;
import hospital.model.dto.DepartmentDto;
import hospital.model.dto.PatientDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DepartmentServlet extends HttpServlet {

    private final DepartmentController departmentController;

    public DepartmentServlet(DepartmentController departmentController) {
        this.departmentController = departmentController;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<DepartmentDto> departments = departmentController.findAll();
            req.setAttribute("departments", departments);
            req.getRequestDispatcher("/WEB-INF/jsp/department/list.jsp").forward(req, resp);
        } else if (pathInfo.equals("/new")) {
            req.getRequestDispatcher("/WEB-INF/jsp/department/form.jsp").forward(req, resp);
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
                DepartmentDto department = departmentController.findById(id);
                List<PatientDto> patients = departmentController.getPatients(id);
                req.setAttribute("department", department);
                req.setAttribute("patients", patients);
                req.getRequestDispatcher("/WEB-INF/jsp/department/detail.jsp").forward(req, resp);
            } else if ("edit".equals(parts[2])) {
                DepartmentDto department = departmentController.findById(id);
                req.setAttribute("department", department);
                req.getRequestDispatcher("/WEB-INF/jsp/department/form.jsp").forward(req, resp);
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
            String name = req.getParameter("name");
            departmentController.create(name);
            resp.sendRedirect(req.getContextPath() + "/departments");
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
                String name = req.getParameter("name");
                departmentController.update(id, name);
                resp.sendRedirect(req.getContextPath() + "/departments/" + id);
            } else if ("delete".equals(parts[2])) {
                departmentController.delete(id);
                resp.sendRedirect(req.getContextPath() + "/departments");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
