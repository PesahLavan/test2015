package ua.com.test.models;

import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Serializable;

public class Employee implements Serializable {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty companyId;
    private SimpleStringProperty name;
    private SimpleIntegerProperty salary;
    private SimpleStringProperty nameCompany;

    public Employee() {
    }

    public Employee(Integer id, Integer companyId, String name, Integer salary) {
        this.id = new SimpleIntegerProperty(id);
        this.companyId = new SimpleIntegerProperty(companyId);
        this.name = new SimpleStringProperty(name);
        this.salary = new SimpleIntegerProperty(salary);
    }

    public Employee(Integer companyId, String name, Integer salary) {
        this.companyId = new SimpleIntegerProperty(companyId);
        this.name = new SimpleStringProperty(name);
        this.salary = new SimpleIntegerProperty(salary);
    }

    public IntegerProperty idProperty() {
        return id ;
    }


    public int getId() {
        return idProperty().get();
    }

    public void setId(int id) {
        idProperty().set(id);
    }

    public IntegerProperty companyIdProperty() {
        return companyId ;
    }

    public int getCompanyId()  {
         return companyIdProperty().get();
    }

    public void setCompanyId(int companyId) {
        companyIdProperty().set(companyId);
    }

    public StringProperty nameProperty(){
        return name;
    }

    public String getName() {
        return nameProperty().get();
    }

    public void setName(String name) {
        nameProperty().set(name);
    }

    public IntegerProperty salaryProperty(){
        return salary;
    }

    public int getSalary() {
        return salaryProperty().get();
    }

    public void setSalary(int salary) {
        salaryProperty().set(salary);
    }
}
