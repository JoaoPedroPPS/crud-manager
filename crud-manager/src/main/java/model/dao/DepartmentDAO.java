package model.dao;

import java.util.List;

import model.Department;
import model.ModelException;

public interface DepartmentDAO {
	boolean save(Department department) throws ModelException;
	boolean update(Department department) throws ModelException;
	boolean delete(Department department) throws ModelException;
	List<Department> listAll() throws ModelException;
	Department findById(int id) throws ModelException;
}
