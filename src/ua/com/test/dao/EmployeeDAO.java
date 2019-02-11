package ua.com.test.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.test.database.SQLiteConnection;
import ua.com.test.controllers.views.View;
import ua.com.test.models.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO extends AbstractDAO{
    private static final Logger log = LoggerFactory.getLogger(EmployeeDAO.class);

    public ObservableList<View> listEmployee(){
        ObservableList<View> list = FXCollections.observableArrayList();
        try (ResultSet rs = SQLiteConnection.getConnection().createStatement().executeQuery("select * from Employee"))
        {
            while (rs.next()) {
                View employee = new View(
                        rs.getInt("id"),
                        rs.getInt("company_id"),
                        rs.getString("name"),
                        rs.getInt("salary"));
                list.add(employee);
            }
            return list;
        }catch (SQLException e){
            log.error("Error read listEmployee to DB", e);
        }
        return list;

    }
    public void updateEmployee (Employee employee){
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement
                ("update Employee set company_id=?,  name=?,  salary=? where id=?"))
        {
            stmt.setInt(1, employee.getCompanyId());
            stmt.setString(2, employee.getName());
            stmt.setInt(3, employee.getSalary());
            stmt.setInt(4, employee.getId());
            stmt.execute();
        } catch (SQLException e) {
            log.error("Error updateEmployee to DB", e);
        }
    }

    public void addEmployee(Employee employee) {
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement
                ("insert into Employee (company_id, name, salary) values(?,?,?)"))
        {
            stmt.setInt(1, employee.getCompanyId());
            stmt.setString(2, employee.getName());
            stmt.setInt(3, employee.getSalary());
            stmt.execute();
        } catch (SQLException e) {
            log.error("Error addEmployee to DB", e);
        }
    }
}
