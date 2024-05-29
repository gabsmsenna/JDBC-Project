package application;

import DataBase.DataBase;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProgramUpdateTest {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DataBase.getConnection();

            ps = conn.prepareStatement(
                    "UPDATE seller "
                    + "SET BaseSalary = BaseSalary + ? "
                    + "WHERE "
                    + "(DepartmentId = ?)");

            ps.setDouble(1, 200.0);
            ps.setInt(2, 2);
            int rowsAffected = ps.executeUpdate();

            System.out.println("Done! Number of rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DataBase.closeStatement(ps);
            DataBase.closeConnection();

        }
    }
}
