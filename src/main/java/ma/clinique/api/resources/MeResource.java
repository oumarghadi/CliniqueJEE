package ma.clinique.api.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import ma.clinique.api.security.AuthPrincipal;

@Path("/me")
@Produces(MediaType.APPLICATION_JSON)
public class MeResource {

  @GET
  public Object me(@Context SecurityContext sc) {
    AuthPrincipal p = (AuthPrincipal) sc.getUserPrincipal();

    return new Object() {
      public final long id = p.getUserId();
      public final String username = p.getName();
      public final String role = p.getRole().name();
    };
  }
}
