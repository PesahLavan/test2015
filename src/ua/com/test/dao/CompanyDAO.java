package ua.com.test.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.test.database.SQLiteConnection;
import ua.com.test.controllers.views.View;
import ua.com.test.models.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDAO extends AbstractDAO{
    private static final Logger log = LoggerFactory.getLogger(CompanyDAO.class);

    public ObservableList<View> listCompany() {
        ObservableList<View> list = FXCollections.observableArrayList();
        try (ResultSet rs = SQLiteConnection.getConnection().createStatement().executeQuery("select * from Company"))
        {
            while (rs.next()) {
                View company = new View(rs.getInt("id"), rs.getString("name"));
                list.add(company);
            }
        }catch (SQLException e){
            log.error("Error read listCompany to DB", e);
        }
        return list;
    }

    public void addCompany(Company company) {
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("insert into Company (name) values(?)"))
        {
            stmt.setString(1, company.getName());
            stmt.execute();
        } catch (SQLException e) {
            log.error("Error addCompany to DB", e);
        }
    }

    public void updateCompany (Company company){
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("update Company set name=? where id=?")) {
            stmt.setString(1, company.getName());
            stmt.setInt(2, company.getId());
            stmt.execute();
        } catch (SQLException e) {
            log.error("Error updateCompany to DB", e);
        }
    }
}
