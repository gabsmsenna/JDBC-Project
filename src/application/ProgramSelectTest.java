package application;

import DataBase.DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProgramSelectTest {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DataBase.getConnection();

            st = conn.createStatement();

            rs = st.executeQuery("SELECT * FROM department");

            while (rs.next()) {
                System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DataBase.closeStatement(st);
            DataBase.closeResultSet(rs);
            DataBase.closeConnection();
        }
    }
}
