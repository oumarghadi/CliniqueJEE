package ma.clinique.api.resources;

import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.Context;

import ma.clinique.api.dto.request.AppointmentCreateRequest;
import ma.clinique.api.dto.request.AppointmentUpdateRequest;
import ma.clinique.api.security.AuthPrincipal;
import ma.clinique.api.security.RequiresRole;
import ma.clinique.config.AppContext;
import ma.clinique.model.Appointment;
import ma.clinique.model.enums.Role;

@Path("/appointments")
@Produces(MediaType.APPLICATION_JSON)
public class AppointmentsResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @RequiresRole(Role.ADMIN)
  public Appointment create(AppointmentCreateRequest req) {
    return AppContext.appointments().create(req.patientId, req.doctorId, req.startAt, req.reason);
  }

  @GET
  @RequiresRole({Role.ADMIN, Role.DOCTOR})
  public List<Appointment> list(@Context SecurityContext ctx) {
    AuthPrincipal p = (AuthPrincipal) ctx.getUserPrincipal();
    if (p.getRole() == Role.ADMIN) return AppContext.appointments().listAll();
    return AppContext.appointments().listByDoctor(p.getUserId());
  }

  @GET
  @Path("/{id}")
  @RequiresRole({Role.ADMIN, Role.DOCTOR})
  public Appointment get(@PathParam("id") long id, @Context SecurityContext ctx) {
    AuthPrincipal p = (AuthPrincipal) ctx.getUserPrincipal();
    return AppContext.appointments().getFor(p.getRole(), p.getUserId(), id);
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @RequiresRole({Role.ADMIN, Role.DOCTOR})
  public Appointment update(@PathParam("id") long id, AppointmentUpdateRequest req, @Context SecurityContext ctx) {
    AuthPrincipal p = (AuthPrincipal) ctx.getUserPrincipal();

    if (p.getRole() == Role.ADMIN) {
      return AppContext.appointments().updateAsAdmin(
          id, req.patientId, req.doctorId, req.startAt, req.reason, req.status
      );
    }

    // DOCTOR : seulement status
    if (req.patientId != null || req.doctorId != null || req.startAt != null || req.reason != null) {
      throw new BadRequestException("Doctor can only update status");
    }
    return AppContext.appointments().updateStatusAsDoctor(p.getUserId(), id, req.status);
  }

  @DELETE
  @Path("/{id}")
  @RequiresRole(Role.ADMIN)
  public void delete(@PathParam("id") long id) {
    AppContext.appointments().delete(id);
  }
}
