package application;

import DataBase.DataBase;

public class ProgramTest {
    public static void main(String[] args) {
        DataBase.getConnection();

        DataBase.closeConnection();
    }
}
