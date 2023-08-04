package dev.vultureweb.generator.impl;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.Dependent;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionManager {

    private static final System.Logger LOGGER = System.getLogger(ConnectionManager.class.getName());

    public AutoClosableConnection connect() {
       try{
           DataSource dataSource = InitialContext.doLookup("java:/PostgresDS");
           return new AutoClosableConnection(dataSource.getConnection());
       } catch (SQLException | NamingException e) {
           LOGGER.log(System.Logger.Level.ERROR, e.getMessage(), e);
           throw new RuntimeException(e);
       }
    }

}