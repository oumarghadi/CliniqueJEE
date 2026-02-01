package ma.clinique.api.resources;

import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ma.clinique.api.dto.request.PatientCreateRequest;
import ma.clinique.api.dto.request.PatientUpdateRequest;
import ma.clinique.api.security.RequiresRole;
import ma.clinique.config.AppContext;
import ma.clinique.model.Patient;
import ma.clinique.model.enums.Role;

@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientsResource {

  @GET
  @RequiresRole(Role.ADMIN)
  public List<Patient> list() {
    return AppContext.patients().list();
  }

  @POST
  @RequiresRole(Role.ADMIN)
  public Patient create(PatientCreateRequest req) {
    if (req == null) throw new BadRequestException("body required");
    return AppContext.patients().create(req.firstName, req.lastName, req.phone, req.birthDate);
  }

  @GET
  @Path("/{id}")
  @RequiresRole(Role.ADMIN)
  public Patient get(@PathParam("id") long id) {
    return AppContext.patients().get(id);
  }

  @PUT
  @Path("/{id}")
  @RequiresRole(Role.ADMIN)
  public Patient update(@PathParam("id") long id, PatientUpdateRequest req) {
    if (req == null) throw new BadRequestException("body required");
    return AppContext.patients().update(id, req.firstName, req.lastName, req.phone, req.birthDate, req.active);
  }

  @DELETE
  @Path("/{id}")
  @RequiresRole(Role.ADMIN)
  public void delete(@PathParam("id") long id) {
    AppContext.patients().delete(id);
  }
}
