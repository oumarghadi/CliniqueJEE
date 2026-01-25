package ma.clinique.api.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import ma.clinique.config.AppContext;

@Path("/me")
@Produces(MediaType.APPLICATION_JSON)
public class MeResource {

  @GET
  public Object me(@HeaderParam("X-User-Id") String userIdHeader) {
    long userId = Long.parseLong(userIdHeader);
    var u = AppContext.users().findById(userId).orElseThrow(NotFoundException::new);

    // réponse simple (sans exposer passwordHash)
    return new Object() {
      public final long id = u.getId();
      public final String username = u.getUsername();
      public final String role = u.getRole().name();
      public final boolean active = u.isActive();
    };
  }
}
