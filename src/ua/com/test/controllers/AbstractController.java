package ua.com.test.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ua.com.test.controllers.views.View;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public abstract class AbstractController{

    private static final String PATH = "/ua/com/test/views/";

    protected void showElement(ActionEvent actionEvent, String fileName, String title) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(PATH + fileName));
        stage.setTitle(title);
        stage.setMinHeight(150);
        stage.setMinWidth(300);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    protected StringConverter<View> converter(){
        return   new StringConverter<View>(){
            @Override
            public String toString(View company) {
                if (company == null){
                    return "";
                } else {
                    return company.getCompanyName();
                }
            }

            @Override
            public View fromString(String string) {
                return null;
            }

        };
    }

    protected ObservableList<View> convertViewForEmployee(List<View> companies, List<View> employees){
        ObservableList<View> result = FXCollections.observableArrayList();
        result.addAll(employees.stream()
                .map(employee -> new View(employee.getIdCompany()
                        , employee.getIdEmployee()
                        , employee.getNameEmployer()
                        , companies.stream()
                                .filter(company -> company.getIdCompany() == employee.getIdCompany())
                                .collect(Collectors.toList()).get(0)
                                .getCompanyName()
                        , employee.getSalary()))
                .collect(Collectors.toList()));
        return result;
    }

    protected ObservableList<View> convertViewForMain(List<View> companies, List<View> employees){
        ObservableList<View> result = FXCollections.observableArrayList();
        result.addAll(companies.stream()
                .map(company -> new View(company.getCompanyName()
                        , countEmployee(employees, company.getIdCompany())
                        , sumSalaryCompany(employees, company.getIdCompany())/countEmployee(employees, company.getIdCompany())
                        , employees.stream()
                            .filter(employee ->  company.getIdCompany() == employee.getIdCompany())
                            .mapToInt(View::getSalary)
                            .max().orElse(0)))
                .collect(Collectors.toList()));
        return result;
    }

    private int countEmployee(List<View> employees, int companyId){
        return (int)employees
                .stream()
                .filter(employee ->  companyId == employee.getIdCompany())
                .count();
    }

    private int sumSalaryCompany(List<View> employees, int companyId){
        return employees.stream().filter(employee ->  companyId == employee.getIdCompany()).mapToInt(View::getSalary).sum();
    }
}
