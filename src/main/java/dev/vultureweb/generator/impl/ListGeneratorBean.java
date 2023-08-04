package dev.vultureweb.generator.impl;

import dev.vultureweb.generator.domain.Element;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ListGeneratorBean {

    private static final System.Logger LOG = System.getLogger(ListGeneratorBean.class.getName());

    public List<Element> generateListOf(int numberOfElements) {
        try(AutoClosableConnection connection = new ConnectionManager().connect()) {
            Element[] elements = new Element[numberOfElements];
            for (int index = 0; index < numberOfElements; index++) {
                elements[index] = new Element(index, UUID.randomUUID().toString(), "an element");
            }
            gerPerson(connection.getConnection());
            return List.copyOf(Arrays.stream(elements).toList());
        } catch (SQLException sqlException) {
            LOG.log(System.Logger.Level.ERROR, sqlException.getMessage());
            throw new RuntimeException(sqlException);
        }
    }

    private void gerPerson(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from trip_planner.person");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UUID uuid = resultSet.getObject("id",UUID.class);
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                LOG.log(System.Logger.Level.ERROR,MessageFormat
                        .format("id: {0}, first_name: {1}, last_name: {2}",
                                uuid, firstName, lastName));
            }
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, e.getMessage());
        }
    }
}
