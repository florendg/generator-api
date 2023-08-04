package dev.vultureweb.generator.api;

import dev.vultureweb.generator.domain.Person;
import dev.vultureweb.generator.impl.PersonTableManager;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/person")
public class PersonApi {

    @Inject
    private PersonTableManager personTableManager;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersons() {
        return personTableManager.getAllPersons();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Person getPersonById(@PathParam("uuid") UUID uuid) {
        return personTableManager.getPersonByUuid(uuid);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person updatePerson(Person person) {
        return personTableManager.updatePerson(person);
    }

    @DELETE
    @Path("/{uuid}")
    public void deletePerson(@PathParam("uuid") UUID uuid){
        personTableManager.deletePerson(uuid);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person createPerson(Person person) {
        return personTableManager.createPerson(person);
    }
}
