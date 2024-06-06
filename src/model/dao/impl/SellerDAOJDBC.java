package model.dao.impl;

import DataBase.DBException;
import DataBase.DataBase;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDAOJDBC implements SellerDAO {

    private Connection conn;

    public SellerDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                    "INSERT INTO seller " +
                         "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                            "VALUES " +
                            "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            statement.setDouble(4, obj.getBaseSalary());
            statement.setInt(5, obj.getDepartment().getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
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
    public void update(Seller obj) {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                    "UPDATE seller " +
                            "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                            "WHERE Id = ?");

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            statement.setDouble(4, obj.getBaseSalary());
            statement.setInt(5, obj.getDepartment().getId());
            statement.setInt(6, obj.getId());

            int rowsAffected = statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DataBase.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                    "DELETE from seller " +
                            "WHERE Id = ?"
            );

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally {
            DataBase.closeStatement(statement);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "ORDER BY Name"
            );
            rs = st.executeQuery();

            Map<Integer, Department> map = new HashMap<>();
            List<Seller> sellersList = new ArrayList<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(dep, rs);
                sellersList.add(obj);
            }
            return sellersList;
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
        finally {
            DataBase.closeStatement(st);
            DataBase.closeResultSet(rs);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.id = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(dep, rs);
                return obj;
            }
            return null;
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
        finally {
            DataBase.closeStatement(st);
            DataBase.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE DepartmentId = ? " +
                            "ORDER BY Name"
            );
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            Map<Integer, Department> map = new HashMap<>();
            List<Seller> sellersList = new ArrayList<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(dep, rs);
                sellersList.add(obj);
            }
            return sellersList;
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
        finally {
            DataBase.closeStatement(st);
            DataBase.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(Department department, ResultSet rs) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setDepartment(department);
        return seller;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
}
