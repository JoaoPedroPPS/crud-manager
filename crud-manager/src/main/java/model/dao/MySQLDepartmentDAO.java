package model.dao;


import java.util.ArrayList;
import java.util.List;
import java.sql.SQLIntegrityConstraintViolationException;
import model.ModelException;
import model.Company;
import model.Department;

public class MySQLDepartmentDAO implements DepartmentDAO {

    @Override
    public boolean save(Department department) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlInsert = "INSERT INTO `crud_manager`.`departments` (`name`, `manager`, `companies_id`, `budget`) VALUES"
                + " (?, ?, ?, ?);";

        db.prepareStatement(sqlInsert);
        
        db.setString(1, department.getNome());
        db.setString(2, department.getManager());
        db.setInt(3, department.getCompanyId());
        db.setFloat(4, department.getBudget());

        return db.executeUpdate() > 0;
        
        
        
        
    }

    @Override
    public boolean update(Department department) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlUpdate = "UPDATE Departments "
                + "SET name = ?, "
                + "manager = ?, "
                + "companies_id = ?, "
                + "budget = ? "
                + "WHERE id = ?";

        db.prepareStatement(sqlUpdate);

        db.setString(1, department.getNome());
        db.setString(2, department.getManager());
        db.setInt(3, department.getCompanyId());
        db.setFloat(4, department.getBudget());
        db.setInt(5, department.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Department department) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlDelete = "DELETE FROM Departments "
                + "WHERE id = ?";

        db.prepareStatement(sqlDelete);
        db.setInt(1, department.getId());

        try {
            return db.executeUpdate() > 0;
        } catch (ModelException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return false;
            }
            throw e;
        }
    }

    @Override
    public List<Department> listAll() throws ModelException {

        DBHandler db = new DBHandler();

        List<Department> departments = new ArrayList<>();

        String sqlQuery = "SELECT * FROM Departments";

        db.createStatement();
        db.executeQuery(sqlQuery);

        while (db.next()) {
            Department department = createDepartment(db);
            departments.add(department);
        }

        return departments;
    }

    @Override
    public Department findById(int id) throws ModelException {

        DBHandler db = new DBHandler();

        String sql = "SELECT * FROM Departments WHERE id = ?";

        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        Department department = null;
        if (db.next()) {
            department = createDepartment(db);
        }

        return department;
    }

    private Department createDepartment(DBHandler db) throws ModelException {
        Department department = new Department(db.getInt("id"));
        department.setNome(db.getString("name"));
        department.setManager(db.getString("manager"));
        department.setBudget(db.getFloat("budget"));
        
        CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class); 
        
        Company company = companyDAO.findById(db.getInt("companies_id")); 
        department.setCompany(company);

        return department;
    }

}
