package ua.com.test.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.test.dao.CompanyDAO;
import ua.com.test.dao.EmployeeDAO;
import ua.com.test.controllers.views.View;
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
    private Button btnAddComp, btnUpdateComp , btnDeleteComp, btnAddEmployee, btnUpdateEmployee, btnDeleteEmployee,
            btnNewEmployee, btnNewCompany;

    @FXML
    private TableView tableCompany, tableEmployee;
    @FXML
    private TableColumn<View, String> columnNameCompSup;
    @FXML
    private TableColumn<View, String> columnNameCompany;
    @FXML
    private TableColumn<View, String> columnName;
    @FXML
    private TableColumn<View, Integer>  columnSalary;
    @FXML
    private TextField textFieldNameCompany, textFieldName, textFieldSalary;
    @FXML
    private ComboBox<View> comboBoxCompany;
    @FXML
    private ObservableList<View> listEmployee = FXCollections.observableArrayList();
    @FXML
    private ObservableList<View> listCompany = FXCollections.observableArrayList();


    private List<Integer> listParam = new LinkedList<>();

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
                    View selectionCompany = (View) tableCompany.getSelectionModel().getSelectedItem();
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
        listCompany = companyDAO.listCompany();
        tableCompany.setItems(listCompany);
    }

    public void initializeEmployee(){
        listCompany = companyDAO.listCompany();
        listEmployee = convertViewForEmployee(listCompany, employeeDAO.listEmployee());
        tableEmployee.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        columnName.setCellValueFactory(new PropertyValueFactory<View, String>("nameEmployee"));
        columnNameCompany.setCellValueFactory(new PropertyValueFactory<View, String>("companyName"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<View, Integer>("salary"));
        comboBoxCompany.setItems(listCompany);
        comboBoxCompany.setConverter(converter());
        tableEmployee.setItems(listEmployee);
        tableEmployee.setRowFactory( tv -> {
            TableRow rowEmployee = new TableRow<>();
            rowEmployee.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! rowEmployee.isEmpty()) ) {
                    View selectionEmployee = (View)tableEmployee.getSelectionModel().getSelectedItem();
                    int id = selectionEmployee.getIdEmployee();
                    listParam.clear();
                    listParam.add(0,id);
                    int salary = selectionEmployee.getSalary();
                    String name = selectionEmployee.getNameEmployer();
                    textFieldName.setText(name);
                    textFieldSalary.setText(String.valueOf(salary));
                    comboBoxCompany.setValue(selectionEmployee);
                    listParam.add(1,selectionEmployee.getIdCompany());
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

        switch (clickedButton.getId()){
            case "btnNewCompany":
                btnUpdateComp.setDisable(true);
                btnDeleteComp.setDisable(true);
                btnAddComp.setDisable(false);
                textFieldNameCompany.setDisable(false);
                textFieldNameCompany.setText("");
                break;

            case "btnAddComp":
                companyDAO.addCompany(new Company(textFieldNameCompany.getText()));
                initializeCompany();
                initializeEmployee();
                break;

            case "btnUpdateComp":
                companyDAO.updateCompany(new Company(listParam.get(0), textFieldNameCompany.getText()));
                tableCompany.refresh();
                initializeCompany();
                initializeEmployee();
                break;

            case "btnDeleteComp":
                int idComp = listParam.get(0) ;
                if (idComp!=1){
                    companyDAO.delete(idComp,"Company");
                    for (int i = 0; i < listEmployee.size(); i++) {
                        if (listEmployee.get(i).getIdCompany()== idComp) {
                            employeeDAO.updateEmployee(
                                    new Employee(listEmployee.get(i).getIdEmployee(),
                                            1, listEmployee.get(i).getNameEmployer(),
                                            0));
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
                    idCompany = comboBoxCompany.getValue().getIdCompany();
                }catch (NullPointerException e)
                {
                    idCompany = 1;
                }
                if (idCompany == 1) salary =0;
                employeeDAO.addEmployee(new Employee(idCompany, name, salary) );
                btnAddEmployee.setDisable(true);
                initializeCompany();
                initializeEmployee();
                break;

            case "btnUpdateEmployee":
                try {
                    salary = Integer.valueOf(textFieldSalary.getText());
                    if (salary <= 0) throw new  NumberFormatException();
                }catch (NumberFormatException e){
                    showMessage(actionEvent);
                    break;
                }
                idCompany = listParam.get(1);
                int idCompanyCh = comboBoxCompany.getValue().getIdCompany();
                if (idCompany != idCompanyCh) idCompany = idCompanyCh;
                if (idCompany == 1) salary =0;
                employeeDAO.updateEmployee(new Employee(listParam.get(0), idCompany, textFieldName.getText(), salary));
                initializeCompany();
                initializeEmployee();
                break;

            case "btnDeleteEmployee":
                employeeDAO.delete(listParam.get(0) ,"Employee");
                initializeCompany();
                initializeEmployee();
                break;
        }
    }

    public void showMessage(ActionEvent actionEvent) {
        try {
            showElement(actionEvent, "message.fxml", "Note of warning");
        } catch (IOException e) {
            log.error("Error showMessage", e);
        }
    }
}

