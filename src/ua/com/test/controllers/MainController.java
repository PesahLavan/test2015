package ua.com.test.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.test.dao.CompanyDAO;
import ua.com.test.dao.EmployeeDAO;
import ua.com.test.controllers.views.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends AbstractController implements Initializable{

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @FXML
    private Button btnEditor;
    @FXML
    private TableView tableMain;
    @FXML
    private TableColumn<View, String> columnCompanyName;
    @FXML
    private TableColumn<View, Integer> columnCountEmployee, columnMediumSalary;
    @FXML
    private Label labelSalary;
    @FXML
    private ObservableList<View> listMain = FXCollections.observableArrayList();


    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMain();
    }


    private CompanyDAO companyDAO = new CompanyDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public void initializeMain(){
        tableMain.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        columnCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        columnCountEmployee.setCellValueFactory(new PropertyValueFactory<>("countEmployee"));
        columnMediumSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        listMain =convertViewForMain(companyDAO.listCompany(), employeeDAO.listEmployee());
        tableMain.setItems(listMain);
        tableMain.getSortOrder().add(columnMediumSalary);

        tableMain.setRowFactory( tv -> {
            TableRow row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    View selectionCompany = (View)tableMain.getSelectionModel().getSelectedItem();
                    int maxSalary = selectionCompany.getMaxSalary();
                    String nameCompany = selectionCompany.getCompanyName();
                    labelSalary.setText(" Company: " + nameCompany + " max salary is " + maxSalary + ".");
                }
            });
            return row ;
        });
    }

    public void showEditor(ActionEvent actionEvent) {
        try {
            showElement(actionEvent, "editorEmployee.fxml", "Editor");
            Stage stage = new Stage();
            stage.setOnCloseRequest(event ->  initializeMain());
        } catch (IOException e) {
            log.error("Error while showEditor", e);
        }
    }
}
