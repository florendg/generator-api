package dev.vultureweb.generator.impl;

import java.sql.Connection;
import java.sql.SQLException;

public class AutoClosableConnection implements AutoCloseable{

    private final Connection connection;

    public AutoClosableConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }
}
