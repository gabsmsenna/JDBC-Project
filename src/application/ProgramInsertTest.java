package application;

import DataBase.DataBase;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProgramInsertTest {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            conn = DataBase.getConnection();

            ps = conn.prepareStatement(
                    "INSERT INTO seller"
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, "Carl Purple");
            ps.setString(2, "carl@gmail.com");
            ps.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
            ps.setDouble(4, 3000.0);
            ps.setInt(5, 4);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0 ) {
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Done! ID = " + id);
                }
            } else {
                System.out.println("No rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            DataBase.closeStatement(ps);
            DataBase.closeConnection();
        }
    }
}
