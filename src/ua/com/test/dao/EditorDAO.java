package ua.com.test.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.com.test.database.SQLiteConnection;
import ua.com.test.interfaces.View;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditorDAO {
    public ObservableList<View> editorList() throws SQLException {

        try (ResultSet rs = SQLiteConnection.getConnection().createStatement().executeQuery("select * from Editor"))
        {
            ObservableList<View> editorList = FXCollections.observableArrayList();
            while (rs.next()) {
                int idEmployee = rs.getInt("Id_Employee");
                int idCompany = rs.getInt("Id_Company");
                String name = rs.getString("Name");
                String nameCompany = rs.getString("Name_company");
                int salary = rs.getInt("Salary");
                View view = new View(idCompany, idEmployee, name, nameCompany, salary);
                editorList.add(view);
            }
            return editorList ;
        }
    }
}
