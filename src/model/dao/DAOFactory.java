package model.dao;

import DataBase.DataBase;
import model.dao.impl.SellerDAOJDBC;

public class DAOFactory {

    public static SellerDAO createSellerDAO() {
        return new SellerDAOJDBC(DataBase.getConnection());
    }
}
