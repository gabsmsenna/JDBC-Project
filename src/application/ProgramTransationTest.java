package application;

import DataBase.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import DataBase.DBException;

public class ProgramTransationTest {
    public static void main(String[] args) {

        Connection conn = null;
        Statement ps = null;

        try {
            conn = DataBase.getConnection();

            conn.setAutoCommit(false);

            ps = conn.createStatement();

            int rows1 = ps.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
            /*
            int x = 1;
            if (x < 2) {
                throw new SQLException("Fake error");
            }
             */

            int rows2 = ps.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

            conn.commit();

            System.out.println("rows1 = " + rows1);
            System.out.println("rows2 = " + rows2);

        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DBException("Transaction rolled back | Caused by " + e.getMessage());
            } catch (SQLException ex) {
                throw new DBException("Error while rolling back | Caused by " + ex.getMessage());
            }
        }

    }
    }

