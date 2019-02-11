package ua.com.test.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {
    private static final Logger log = LoggerFactory.getLogger(CreateDB.class);
    private CreateDB() {

    }

    public static void createDB(){
        try {
            Statement stmt = SQLiteConnection.getConnection().createStatement();

            String sqlCompany = "CREATE TABLE [Company] " +
                    "([id] INTEGER NOT NULL PRIMARY KEY," +
                    " [name] VARCHAR NOT NULL UNIQUE)";

            String sqlEmployee = "CREATE TABLE [Employee]" +
                    "([id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " [company_id] INT NOT NULL CONSTRAINT [fk_company] REFERENCES [Company]([id]) ON DELETE RESTRICT," +
                    " [name] VARCHAR NOT NULL UNIQUE,  " +
                    "  [salary] INT)";

            stmt.executeUpdate(sqlCompany);
            stmt.executeUpdate(sqlEmployee);
            stmt.close();
            PreparedStatement stmtm = SQLiteConnection.getConnection().prepareStatement("insert into Company (name) values('Unemployed')");
            stmtm.execute();
            stmtm.close();
        } catch ( SQLException e ) {
            log.error("Error creating database", e);
        }
    }

}
