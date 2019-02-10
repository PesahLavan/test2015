package ua.com.test.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.com.test.database.SQLiteConnection;
import ua.com.test.models.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO extends AbstractDAO{
    public ObservableList<Employee> listEmployee() throws SQLException {
        try (ResultSet rs = SQLiteConnection.getConnection().createStatement().executeQuery("select * from Employee"))
        {
            ObservableList<Employee> list = FXCollections.observableArrayList();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setCompanyId(rs.getInt("company_id"));
                employee.setName(rs.getString("name"));
                employee.setSalary(rs.getInt("salary"));
                list.add(employee);
            }
            return list;
        }
    }
    public void updateEmployee (int idEmployee, int idCompany, String name, int salary){
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement
                ("update Employee set id_company=?,  name=?,  salary=? where id=?"))
        {
            stmt.setInt(1, idCompany);
            stmt.setString(2, name);
            stmt.setInt(3, salary);
            stmt.setInt(4, idEmployee);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {e.printStackTrace();}
    }

    public void addEmployee(int idCompany, String name, int salary, String nameTable) {
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement
                ("insert into " + nameTable + "(id_company, name, salary) values(?,?,?)"))
        {
            stmt.setInt(1, idCompany);
            stmt.setString(2, name);
            stmt.setInt(3, salary);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {e.printStackTrace();}
    }
}
