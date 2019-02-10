package ua.com.test.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.com.test.database.SQLiteConnection;
import ua.com.test.interfaces.View;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainDAO {
    public ObservableList<View> listMain() throws SQLException {

        ObservableList<View> list = null;
        try ( ResultSet rs = SQLiteConnection.getConnection().createStatement().executeQuery("select * from Main"))
        {
            list = FXCollections.observableArrayList();
            while (rs.next()) {
                String nameCompany = rs.getString("Name company");
                int countEmployee = rs.getInt("Count employee");
                int salary = rs.getInt("Average salary");
                int maxSalary = rs.getInt("Max salary");
                View mainView = new View(nameCompany, countEmployee, salary, maxSalary);
                list.add(mainView);
            }
        }
        return list;
    }
}
