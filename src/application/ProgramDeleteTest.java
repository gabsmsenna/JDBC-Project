package application;

import DataBase.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import DataBase.DbIntegrityException;

public class ProgramDeleteTest {
    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DataBase.getConnection();

            ps = conn.prepareStatement(
                    "DELETE FROM department "
                    + "WHERE "
                    + "Id = ?");

            ps.setInt(1, 2);

            int numbersOfRowsAffected = ps.executeUpdate();
            System.out.println("Done! Number of rows affected: " + numbersOfRowsAffected);
        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
    }
}
