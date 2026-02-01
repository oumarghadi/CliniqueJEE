package ma.clinique.api.resources;

import java.util.List;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import ma.clinique.api.dto.request.AppointmentCreateRequest;
import ma.clinique.api.dto.request.AppointmentStatusRequest;
import ma.clinique.api.security.AuthPrincipal;
import ma.clinique.api.security.RequiresRole;
import ma.clinique.config.AppContext;
import ma.clinique.model.Appointment;
import ma.clinique.model.enums.Role;

@Path("/appointments")
@Produces(MediaType.APPLICATION_JSON)
public class AppointmentsResource {

  @GET
  @RequiresRole({Role.ADMIN, Role.DOCTOR})
  public List<Appointment> list(@Context SecurityContext sc) {
    AuthPrincipal p = (AuthPrincipal) sc.getUserPrincipal();
    if (p.getRole() == Role.ADMIN) return AppContext.appointments().listAll();
    return AppContext.appointments().listByDoctor(p.getUserId());
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @RequiresRole(Role.ADMIN)
  public Appointment create(AppointmentCreateRequest req) {
    return AppContext.appointments().create(req.patientId, req.doctorId, req.startAt, req.reason);
  }

  @PUT
  @Path("/{id}/status")
  @Consumes(MediaType.APPLICATION_JSON)
  @RequiresRole({Role.ADMIN, Role.DOCTOR})
  public Appointment updateStatus(@PathParam("id") long id, AppointmentStatusRequest req, @Context SecurityContext sc) {
    if (req == null || req.status == null) throw new BadRequestException("status required");

    // Doctor ne peut modifier que ses rendez-vous
    AuthPrincipal p = (AuthPrincipal) sc.getUserPrincipal();
    if (p.getRole() == Role.DOCTOR) {
      Appointment a = AppContext.appointments().get(id);
      if (a.getDoctorId() != p.getUserId()) throw new ForbiddenException("Forbidden");
    }

    return AppContext.appointments().updateStatus(id, req.status);
  }

  @DELETE
  @Path("/{id}")
  @RequiresRole(Role.ADMIN)
  public void delete(@PathParam("id") long id) {
    AppContext.appointments().delete(id);
  }
}
