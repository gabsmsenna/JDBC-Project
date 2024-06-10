package application;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();

        System.out.println("==== TEST 1: findById ====");
        Department department = departmentDAO.findById(1);
        System.out.println(department);

        System.out.println("=== TEST 2: findByName ====");
        List<Department> list = departmentDAO.findAll();
        for (Department d : list) {
            System.out.println(d);
        }

        System.out.println("\n=== TEST 3: INSERT ===");
        Department newDepartment = new Department(null, "Marketing");
        departmentDAO.insert(newDepartment);
        System.out.println("New department created: " + newDepartment);

        System.out.println("\n=== TEST 4: UPDATE ===");
        Department updatedDepartment = departmentDAO.findById(2);
        updatedDepartment.setName("Contability");
        departmentDAO.update(updatedDepartment);
        System.out.println("Updated completed!");

    }
}
