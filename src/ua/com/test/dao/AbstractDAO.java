package ua.com.test.dao;

import ua.com.test.database.SQLiteConnection;
import ua.com.test.interfaces.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDAO {
    public void delete (int id, String nameTable) {
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("delete from " + nameTable + " where id=?");) {
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) { e.printStackTrace();}
    }
}
