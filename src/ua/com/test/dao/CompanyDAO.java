package ua.com.test.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.com.test.database.SQLiteConnection;
import ua.com.test.models.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDAO extends AbstractDAO{
    public ObservableList<Company> listCompany() throws SQLException {
        try (ResultSet rs = SQLiteConnection.getConnection().createStatement().executeQuery("select * from Company"))
        {
            ObservableList<Company> list = FXCollections.observableArrayList();
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt("id"));
                company.setName(rs.getString("name"));
                list.add(company);
            }
            return list;
        }
    }

    public void addCompany(Company company) {
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("insert into Company (name) values(?)"))
        {
            stmt.setString(1, company.getName());
            stmt.execute();
        } catch (SQLException e) { e.printStackTrace();}
    }

    public void updateCompany (Company company){
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("update Company set name=? where id=?")) {
            stmt.setString(1, company.getName());
            stmt.setInt(2, company.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) { e.printStackTrace();}
    }
}
