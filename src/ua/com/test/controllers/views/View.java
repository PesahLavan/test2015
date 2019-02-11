package ua.com.test.controllers.views;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class View implements Serializable{
    private SimpleIntegerProperty idCompany = new SimpleIntegerProperty(0);
    private SimpleStringProperty nameCompany = new SimpleStringProperty("");
    private SimpleIntegerProperty idEmployee = new SimpleIntegerProperty(0);
    private SimpleStringProperty nameEmployee = new SimpleStringProperty("");
    private SimpleIntegerProperty salary = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty maxSalary = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty countEmployee = new SimpleIntegerProperty(0);

    public View(int idCompany, String nameCompany) {
        this.idCompany = new SimpleIntegerProperty(idCompany);
        this.nameCompany = new SimpleStringProperty(nameCompany);
    }

    public View(int idCompany, int idEmployee, String nameEmployee, String nameCompany, int salary) {
        this.idCompany = new SimpleIntegerProperty(idCompany);
        this.idEmployee = new SimpleIntegerProperty(idEmployee);
        this.nameCompany = new SimpleStringProperty(nameCompany);
        this.nameEmployee = new SimpleStringProperty(nameEmployee);
        this.salary = new SimpleIntegerProperty(salary);
    }
    public View(String nameCompany, int countEmployee, int salary, int maxSalary){
        this.nameCompany = new SimpleStringProperty(nameCompany);
        this.countEmployee = new SimpleIntegerProperty(countEmployee);
        this.salary = new SimpleIntegerProperty(salary);
        this.maxSalary = new SimpleIntegerProperty(maxSalary);
    }

    public View(int id, int idCompany, String nameEmployee, int salary) {
        this.idEmployee = new SimpleIntegerProperty(id);
        this.idCompany = new SimpleIntegerProperty(idCompany);
        this.nameEmployee = new SimpleStringProperty(nameEmployee);
        this.salary = new SimpleIntegerProperty(salary);
    }

    public IntegerProperty idEmployeeProperty() {
        return idEmployee ;
    }
    public int getIdEmployee() {
        return idEmployeeProperty().get();
    }
    public void setIdEmployee(int idEmployee) {
        idCompanyProperty().set(idEmployee);
    }


    public IntegerProperty idCompanyProperty() {
        return idCompany ;
    }
    public int getIdCompany() {
        return idCompanyProperty().get();
    }
    public void setIdCompany(int idCompany) {
        idCompanyProperty().set(idCompany);
    }

    public StringProperty nameEmployeeProperty() {
        return nameEmployee ;
    }
    public String getNameEmployer() {
        return nameEmployeeProperty().get();
    }
    public void setNameEmployer(String nameEmployee) {
        nameEmployeeProperty().set(nameEmployee);
    }

    public StringProperty companyNameProperty() {
        return nameCompany ;
    }
    public String getCompanyName() {
        return companyNameProperty().get();
    }
    public void setCompanyName(String companyName) {
        companyNameProperty().set(companyName);
    }

    public IntegerProperty salaryProperty() {
        return salary ;
    }
    public final int getSalary() {
        return salaryProperty().get();
    }
    public final void setSalary(int salary) {
        salaryProperty().set(salary);
    }

    public IntegerProperty maxSalaryProperty() {
        return maxSalary ;
    }
    public final int getMaxSalary() {
        return maxSalaryProperty().get();
    }
    public final void setMaxSalary(int maxSalary) {
        salaryProperty().set(maxSalary);
    }

    public IntegerProperty countEmployeeProperty() {
        return countEmployee ;
    }
    public final int getCountEmployee() {
        return countEmployeeProperty().get();
    }
    public final void setCountEmployee(int countEmployee) {
        salaryProperty().set(countEmployee);
    }
}
