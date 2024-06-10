package model.dao.impl;

import DataBase.DBException;
import DataBase.DataBase;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDAOJDBC implements DepartmentDAO {

    private Connection conn;

    public DepartmentDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                    "INSERT INTO department " +
                            "(Name) " +
                            "VALUES " +
                            "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getName());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = null;
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DataBase.closeResultSet(resultSet);
            } else {
                throw new DBException("Insert failed");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DataBase.closeStatement(statement);
        }

    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void deleteById(Department obj) {

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement(
                    "SELECT * " +
                            "FROM department " +
                            "WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Department obj = instantiateDepartment(resultSet);
                return obj;
            }
            DataBase.closeResultSet(resultSet);
            DataBase.closeStatement(preparedStatement);
            return null;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public List <Department> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement(
                    "SELECT * " +
                            "FROM department "
            );
            resultSet = preparedStatement.executeQuery();

            Map<Integer,Department> map = new HashMap<>();
            List<Department> list = new ArrayList<>();

           while (resultSet.next()) {
               Department department = map.get(resultSet.getInt("Id"));

               if (department == null) {
                   department = instantiateDepartment(resultSet);
                   map.put(resultSet.getInt("Id"), department);
               }

               Department obj = instantiateDepartment(resultSet);
               list.add(obj);
           }

            DataBase.closeResultSet(resultSet);
           DataBase.closeStatement(preparedStatement);
            return list;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }


    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }
}


