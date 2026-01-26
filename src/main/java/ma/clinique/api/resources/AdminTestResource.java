package ma.clinique.api.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import ma.clinique.api.security.RequiresRole;
import ma.clinique.model.enums.Role;

@Path("/admin/test")
@Produces(MediaType.TEXT_PLAIN)
@RequiresRole(Role.ADMIN)
public class AdminTestResource {

  @GET
  public String ok() {
    return "ADMIN_OK";
  }
}
