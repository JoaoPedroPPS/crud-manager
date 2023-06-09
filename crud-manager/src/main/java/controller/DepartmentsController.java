package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.Department;
import model.ModelException;
import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.dao.CompanyDAO;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/departments", "/department/form", 
		"/department/insert", "/department/delete"})

public class DepartmentsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        switch (action) {
            case "/crud-manager/department/form": {
                ControllerUtil.forward(req, resp, "/form-department.jsp");
                break;
            }
            case "/crud-manager/department/update": {
                Department d = loadDepartment(req);
                req.setAttribute("department", d);
                ControllerUtil.forward(req, resp, "/form-department.jsp");
                break;
            }
            default:
                listDepartments(req);

                ControllerUtil.transferSessionMessagesToRequest(req);

                ControllerUtil.forward(req, resp, "/departments.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        if (action == null || action.equals("")) {
            ControllerUtil.forward(req, resp, "/index.jsp");
            return;
        }

        switch (action) {
            case "/crud-manager/department/delete":
                deleteDepartment(req, resp);
                break;
            case "/crud-manager/department/insert": {
                insertDepartment(req, resp);
                break;
            }
            case "/crud-manager/department/update": {
                updateDepartment(req, resp);
                break;
            }
            default:
                System.out.println("URL inválida " + action);
        }

        ControllerUtil.redirect(resp, req.getContextPath() + "/departments");
    }

    private Department loadDepartment(HttpServletRequest req) {
        String departmentIdParameter = req.getParameter("departmentId");

        int departmentId = Integer.parseInt(departmentIdParameter);

        DepartmentDAO dao = DAOFactory.createDAO(DepartmentDAO.class);

        try {
            Department d = dao.findById(departmentId);

            if (d == null)
                throw new ModelException("Departamento não encontrado para alteração");

            return d;
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }

        return null;
    }

    private void updateDepartment(HttpServletRequest req, HttpServletResponse resp) {
        String departmentName = req.getParameter("name");
        String departmentManager = req.getParameter("manager");
        float departmentBudget = Float.parseFloat(req.getParameter("budget"));
        int companyId = Integer.parseInt(req.getParameter("company"));

        Department department = loadDepartment(req);
        department.setNome(departmentName);
        department.setManager(departmentManager);
        department.setBudget(departmentBudget);
        department.setCompany(new Company(companyId));

        DepartmentDAO dao = DAOFactory.createDAO(DepartmentDAO.class);

        try {
            if (dao.update(department)) {
                ControllerUtil.sucessMessage(req, "Departamento '" + department.getNome() + "' atualizado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Departamento '" + department.getNome() + "' não pode ser atualizado.");
            }
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void insertDepartment(HttpServletRequest req, HttpServletResponse resp) {
        String departmentName = req.getParameter("name");
        String departmentManager = req.getParameter("manager");
        float departmentBudget = Float.parseFloat(req.getParameter("budget"));
        Integer companyId = Integer.parseInt(req.getParameter("company"));

        Department department = new Department();
        department.setNome(departmentName);
        department.setManager(departmentManager);
        department.setBudget(departmentBudget);
        department.setCompany(new Company(companyId));

        DepartmentDAO dao = DAOFactory.createDAO(DepartmentDAO.class);

        try {
            if (dao.save(department)) {
                ControllerUtil.sucessMessage(req, "Departamento '" + department.getNome() + "' salvo com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Departamento '" + department.getNome() + "' não pode ser salvo.");
            }

        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void deleteDepartment(HttpServletRequest req, HttpServletResponse resp) {
        String departmentIdParameter = req.getParameter("id");

        int departmentId = Integer.parseInt(departmentIdParameter);

        DepartmentDAO dao = DAOFactory.createDAO(DepartmentDAO.class);

        try {
            Department d = dao.findById(departmentId);

            if (d == null)
                throw new ModelException("Departamento não encontrado para deleção");

            if (dao.delete(d)) {
                ControllerUtil.sucessMessage(req, "Departamento '" + d.getNome() + "' deletado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "Departamento '" + d.getNome() + "' não pode ser deletado.");
            }
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void listDepartments(HttpServletRequest req) {
        DepartmentDAO departmentDAO = DAOFactory.createDAO(DepartmentDAO.class);
        CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class); 

        List<Department> departments = null;
        List<Company> companies = null;

        try {
            departments = departmentDAO.listAll();
            companies = companyDAO.listAll(); // Carrega a lista de empresas
        } catch (ModelException e) {
            e.printStackTrace();
        }

        if (departments != null)
            req.setAttribute("departments", departments);

        if (companies != null)
            req.setAttribute("companies", companies); // Armazena a lista de empresas no escopo da requisição
    }

}
