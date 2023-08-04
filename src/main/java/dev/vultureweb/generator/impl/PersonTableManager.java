package dev.vultureweb.generator.impl;

import dev.vultureweb.generator.domain.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PersonTableManager {

    @Inject
    private ConnectionManager connectionManager;

    public List<Person> getAllPersons() {
        try (Connection connection = connectionManager.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM trip_planner.person");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Person> persons = new ArrayList<>();
            while (resultSet.next()) {
                Person person = mapToPerson(resultSet);
                persons.add(person);
            }
            return persons;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to get all persons", sqlException);
        }
    }

    public Person getPersonByUuid(UUID uuid) {
        try (Connection connection = new ConnectionManager().connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM trip_planner.person WHERE id = ?");
            preparedStatement.setObject(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapToPerson(resultSet);
            }
            return null;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to get person by uuid", sqlException);
        }
    }

    public Person createPerson(Person person) {
        try (Connection connection = new ConnectionManager().connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            INSERT INTO trip_planner.person (
                                first_name,
                                last_name,
                                email,
                                job_role,
                                rating
                            )
                            VALUES (?, ?, ?, ?, ?)
                         """, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.firstName());
            preparedStatement.setString(2, person.lastName());
            preparedStatement.setString(3, person.email());
            preparedStatement.setString(4, person.jobRole());
            preparedStatement.setBoolean(5, person.rating());
            int affectedRows  = preparedStatement.executeUpdate();
            if(affectedRows == 0) {
                throw new RuntimeException("Failed to create person");
            } else {
                try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        UUID uuid = generatedKeys.getObject("id", java.util.UUID.class);
                        return new Person(uuid, person.firstName(), person.lastName(), person.email(), person.jobRole(), person.rating());
                    }
                    throw new RuntimeException("Failed to create person: could not retrieve uuid");
                }
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to create person", sqlException);
        }
    }

    public Person updatePerson(Person person) {
        try (Connection connection = new ConnectionManager().connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            UPDATE trip_planner.person
                            SET
                                first_name = ?,
                                last_name = ?,
                                email = ?,
                                job_role = ?,
                                rating = ?
                            WHERE id = ?
                         """);
            preparedStatement.setString(1, person.firstName());
            preparedStatement.setString(2, person.lastName());
            preparedStatement.setString(3, person.email());
            preparedStatement.setString(4, person.jobRole());
            preparedStatement.setBoolean(5, person.rating());
            preparedStatement.setObject(6, person.id());
            int affectedRows  = preparedStatement.executeUpdate();
            if(affectedRows == 0) {
                throw new RuntimeException("Failed to update person");
            } else {
                return person;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to update person", sqlException);
        }
    }

    public void deletePerson(UUID uuid) {
        try (Connection connection = new ConnectionManager().connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM trip_planner.person WHERE id = ?");
            preparedStatement.setObject(1, uuid);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0) {
               System.out.println("Person identified uuid: " + uuid.toString() + " does not exist");
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to delete person", sqlException);
        }
    }

    private static Person mapToPerson(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getObject("id", java.util.UUID.class),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("job_role"),
                resultSet.getBoolean("rating")
        );
    }
}
