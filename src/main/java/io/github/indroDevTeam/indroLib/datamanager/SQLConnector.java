package io.github.indroDevTeam.indroLib.datamanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLConnector {
    /*
     *  +-----------------------------------------------+
     *  |                 SQL Connector:                |
     *  |   This section connects to the database and   |
     *  |    sets the credentials for the connection    |
     *  +-----------------------------------------------+
     */

    public enum Status {
        /**
         * When Status is NOT_READY this class has been initialized incorrectly
         */
        NOT_READY,
        READY,
        CONNECTING,
        CONNECTED,
        DISCONNECTING,
        DISCONNECTED
    }

    public Connection connection;
    private final Plugin PLUGIN;
    private final boolean USE_SQLITE;
    private final String PASSWORD;
    private final String HOST;
    private final String PORT;
    private final String DATABASE;
    private final String USERNAME;
    private Status status = Status.NOT_READY;

    public boolean isUseSQLite() {
        return this.USE_SQLITE;
    }
    public Status getStatus() {return this.status;}

    /**
     * @param database What is the name of the database you want to connect to?
     * @param host     Where is the database being hosted? If it's on this machine set this parameter to localhost.
     * @param port     What port is the server running on, the default port for MySQL is 3306.
     * @param username What user do you want to access the database as? Should be set to 'root' if possible.
     * @param password What password does that username use? If none just leave as an empty string "".
     */
    public SQLConnector(String database, String host, String port, String username, String password,
                        boolean useSQLite, Plugin plugin) {
        this.PASSWORD = password;
        this.USERNAME = username;
        this.DATABASE = database;
        this.HOST = host;
        this.PORT = port;

        this.USE_SQLITE = useSQLite;
        this.PLUGIN = plugin;
        this.status = Status.READY;
    }

    public Connection getMySQLConnection() {
        this.status = Status.CONNECTING;
        connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + this.HOST + ":" + this.PORT + "/" + this.DATABASE + "?allowPublicKeyRetrieval=true&useSSL=false",
                    USERNAME,
                    PASSWORD);
        } catch (SQLException e) {
            printSQLException(e);
            return null;
        }
        this.status = Status.CONNECTED;
        return connection;
    }

    public void closeConnection(Connection conn) {
        this.status = Status.DISCONNECTING;
        try {
            conn.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        this.status = Status.DISCONNECTED;
    }

    public Connection getSQLiteConnection() {
        this.status = Status.CONNECTING;
        if (!this.PLUGIN.getDataFolder().exists()) {
            try {
                this.PLUGIN.getDataFolder().mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File dataFolder = new File(PLUGIN.getDataFolder(), DATABASE + ".db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                PLUGIN.getLogger().log(Level.SEVERE, "File write error: " + DATABASE + ".db");
            }
        }
        try {
            if (connection != null && !connection.isClosed()) {
                this.status = Status.CONNECTED;
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            this.status = Status.CONNECTED;
            return connection;
        } catch (SQLException ex) {
            PLUGIN.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            PLUGIN.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                System.err.println("SQLState: " + ((SQLException) e).getSQLState() + "\n");
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode() + "\n");
                System.err.println("Message: " + e.getMessage() + "\n");
                System.err.println("The database is not connected! please ensure that the login credentials are " +
                        "correct and the database is running!");
            }
        }
        this.status = Status.NOT_READY;
    }
}
