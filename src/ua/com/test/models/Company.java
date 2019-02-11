package ua.com.test.models;

import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


public class Company implements Serializable {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;

    public Company() {
    }

    public Company(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public Company(Integer id, String name){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
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

    public StringProperty nameProperty(){
        return name;
    }

    public String getName() {
        return nameProperty().get();
    }

    public void setName(String name) {
        nameProperty().set(name);
    }
}
