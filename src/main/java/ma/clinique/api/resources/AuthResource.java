package ma.clinique.api.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import ma.clinique.api.dto.request.LoginRequest;
import ma.clinique.api.dto.response.TokenResponse;
import ma.clinique.config.AppContext;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  public TokenResponse login(LoginRequest req) {
    if (req == null || req.username == null || req.password == null) {
      throw new BadRequestException("username/password required");
    }
    var session = AppContext.auth().login(req.username, req.password);
    return new TokenResponse(session.getToken());
  }

  @POST
  @Path("/logout")
  public void logout(@HeaderParam("Authorization") String authHeader) {
    String token = extractToken(authHeader);
    AppContext.auth().logout(token);
  }

  private String extractToken(String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new NotAuthorizedException("Bearer token required");
    }
    return authHeader.substring("Bearer ".length()).trim();
  }
}
