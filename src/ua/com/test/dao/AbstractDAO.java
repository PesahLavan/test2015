package ua.com.test.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.test.database.SQLiteConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDAO {
    private static final Logger log = LoggerFactory.getLogger(AbstractDAO.class);
    public void delete (int id, String nameTable) {
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("delete from " + nameTable + " where id=?");) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            log.error("Error delete row to DB", e);
        }
    }
}
