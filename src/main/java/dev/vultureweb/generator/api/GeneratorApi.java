package dev.vultureweb.generator.api;

import dev.vultureweb.generator.domain.Element;
import dev.vultureweb.generator.impl.ListGeneratorBean;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/generate")
public class GeneratorApi {

   @Inject
   ListGeneratorBean impl;

   @GET()
   @Produces(MediaType.APPLICATION_JSON)
   public List<Element> generateList() {
      return impl.generateListOf(10000);
   }
}
