package ua.com.test.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.test.dao.CompanyDAO;
import ua.com.test.dao.EditorDAO;
import ua.com.test.dao.EmployeeDAO;
import ua.com.test.interfaces.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import ua.com.test.models.Company;
import ua.com.test.models.Employee;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class EditorController extends AbstractController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(EditorController.class);

    @FXML
    private Button btnAddComp, btnUpdateComp , btnDeleteComp, btnAddEmployee, btnUpdateEmployee, btnDeleteEmployee;

    @FXML
    private TableView tableCompany, tableEmployee;
    @FXML
    private TableColumn<View, String> columnNameCompSup, columnNameCompany, columnName;
    @FXML
    private TableColumn<View, Integer>  columnSalary;
    @FXML
    private TextField textFieldNameCompany, textFieldName, textFieldSalary;
    @FXML
    private ComboBox<Company> comboBoxCompany;
    @FXML
    private ObservableList<Employee> listEmployee = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Company> listCompany = FXCollections.observableArrayList();


    private List<Integer> listParam = new LinkedList<>();

    private EditorDAO editorDAO = new EditorDAO();
    private CompanyDAO companyDAO = new CompanyDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCompany();
        initializeEmployee();
    }

    public void initializeCompany(){
        tableCompany.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        columnNameCompSup.setCellValueFactory(new PropertyValueFactory<View, String>("companyName"));
        tableCompany.setRowFactory( tv -> {
            TableRow rowCompany = new TableRow<>();
            rowCompany.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! rowCompany.isEmpty()) ) {
                    View selectionCompany = (View)tableCompany.getSelectionModel().getSelectedItem();
                    String nameCompany = selectionCompany.getCompanyName();
                    int id = selectionCompany.getIdCompany();
                    listParam.clear();
                    listParam.add(0,id);
                    textFieldNameCompany.setText(nameCompany);
                    btnAddComp.setDisable(true);
                    btnUpdateComp.setDisable(false);
                    btnDeleteComp.setDisable(false);
                    textFieldNameCompany.setDisable(false);
                }
            });
            return rowCompany ;
        });
        btnAddComp.setDisable(true);
        btnUpdateComp.setDisable(true);
        btnDeleteComp.setDisable(true);
        textFieldNameCompany.setDisable(true);
        textFieldNameCompany.setText("");

        try {
            listCompany = companyDAO.listCompany();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableCompany.setItems(listCompany);
    }

    public void initializeEmployee(){
        try {
            listEmployee = employeeDAO.listEmployee();
            listCompany = companyDAO.listCompany();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableEmployee.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        columnName.setCellValueFactory(new PropertyValueFactory<View, String>("nameEmployee"));
        columnNameCompany.setCellValueFactory(new PropertyValueFactory<View, String>("companyName"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<View, Integer>("salary"));

        comboBoxCompany.setItems(listCompany);
        comboBoxCompany.setConverter(new StringConverter<Company>() {
            @Override
            public String toString(Company company) {
                if (company == null){
                    return "";
                } else {
                    return company.getName();
                }
            }

            @Override
            public Company fromString(String string) {
                return null;
            }
        });

        tableEmployee.setItems(listEmployee);

        tableEmployee.setRowFactory( tv -> {
            TableRow rowEmployee = new TableRow<>();
            rowEmployee.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! rowEmployee.isEmpty()) ) {
                    Employee selectionEmployee = (Employee) tableEmployee.getSelectionModel().getSelectedItem();
                    int id = selectionEmployee.getId();
                    listParam.clear();
                    listParam.add(0,id);
                    int salary = selectionEmployee.getSalary();
                    String name = selectionEmployee.getName();
                    textFieldName.setText(name);
                    textFieldSalary.setText(String.valueOf(salary));
                    comboBoxCompany.setValue(selectionEmployee);
                    listParam.add(1,selectionEmployee.getCompanyId());
                    btnUpdateEmployee.setDisable(false);
                    btnDeleteEmployee.setDisable(false);
                    btnAddEmployee.setDisable(true);
                    textFieldName.setDisable(false);
                    textFieldSalary.setDisable(false);
                    comboBoxCompany.setDisable(false);
                }
            });
            return rowEmployee ;
        });
        btnUpdateEmployee.setDisable(true);
        btnDeleteEmployee.setDisable(true);
        btnAddEmployee.setDisable(true);
        textFieldName.setDisable(true);
        textFieldSalary.setDisable(true);
        comboBoxCompany.setDisable(true);
        textFieldName.setText("");
        comboBoxCompany.setValue(null);
        textFieldSalary.setText("");

    }

    public void actionBtn(ActionEvent actionEvent) throws SQLException {

        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;
        Company company = new Company();
        switch (clickedButton.getId()){
            case "btnNewCompany":
                btnUpdateComp.setDisable(true);
                btnDeleteComp.setDisable(true);
                btnAddComp.setDisable(false);
                textFieldNameCompany.setDisable(false);
                textFieldNameCompany.setText("");
                break;

            case "btnAddComp":
                company.setId(null);
                company.setName(textFieldNameCompany.getText());
                companyDAO.addCompany(company);
                initializeCompany();
                initializeEmployee();
                break;

            case "btnUpdateComp":
                company.setId(listParam.get(0));
                company.setName(textFieldNameCompany.getText());
                companyDAO.updateCompany(company);
                tableCompany.refresh();
                initializeCompany();
                initializeEmployee();
                break;

            case "btnDeleteComp":
                int companyId = listParam.get(0) ;
                if (companyId!=1){
                    companyDAO.delete(companyId,"Company");
                    for (int i = 0; i < listEmployee.size(); i++) {
                        if (listEmployee.get(i).getCompanyId() == companyId) {
                            employeeDAO.updateEmployee(listEmployee.get(i).getId(),1, listEmployee.get(i).getName(), 0);
                        }
                    }
                    initializeCompany();
                    initializeEmployee();
                    break;
                }
                else { initializeCompany();
                    initializeEmployee();}
                break;

            case "btnNewEmployee":
                btnUpdateEmployee.setDisable(true);
                btnDeleteEmployee.setDisable(true);
                btnAddEmployee.setDisable(false);
                textFieldName.setDisable(false);
                textFieldSalary.setDisable(false);
                comboBoxCompany.setDisable(false);
                textFieldName.setText("");
                textFieldSalary.setText("");
                comboBoxCompany.setValue(null);
                break;

            case "btnAddEmployee":
                String name = textFieldName.getText();
                int salary;
                if (textFieldSalary.getText().equals("")) salary = 0;
                else
                try {
                    salary = Integer.valueOf(textFieldSalary.getText());
                    if (salary < 0) throw new  NumberFormatException();

                }catch (NumberFormatException e){
                    showMessage(actionEvent);
                    break;
                }
                int idCompany;
                try {
                    idCompany = comboBoxCompany.getValue().getId();
                }catch (NullPointerException e)
                {
                    idCompany = 1;
                }
                if (idCompany == 1) salary =0;
                employeeDAO.addEmployee(idCompany, name, salary, "Employee");
                btnAddEmployee.setDisable(true);
                initializeCompany();
                initializeEmployee();
                break;

            case "btnUpdateEmployee":
                int idEmployee = listParam.get(0);
                name = textFieldName.getText();
                try {
                    salary = Integer.valueOf(textFieldSalary.getText());
                    if (salary <= 0) throw new  NumberFormatException();
                }catch (NumberFormatException e){
                    showMessage(actionEvent);
                    break;
                }

                idCompany = listParam.get(1);
                int idCompanyCh = comboBoxCompany.getValue().getId();
                if (idCompany != idCompanyCh) idCompany = idCompanyCh;
                if (idCompany == 1) salary =0;
                employeeDAO.updateEmployee(idEmployee,idCompany, name, salary/*, "Editor"*/);
                initializeCompany();
                initializeEmployee();
                break;

            case "btnDeleteEmployee":
                idEmployee = listParam.get(0) ;
                employeeDAO.delete(idEmployee,"Employee");
                initializeCompany();
                initializeEmployee();
                break;
        }
    }

    public void showMessage(ActionEvent actionEvent) {
        try {
            showElement(actionEvent, "message.fxml", "Note of warning");
        } catch (IOException e) {
            log.error("Error while showMessage", e);
        }
    }
}

