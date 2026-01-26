package ma.clinique.api.filters;

import java.io.IOException;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

import ma.clinique.config.AppContext;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext ctx) throws IOException {
    String path = ctx.getUriInfo().getPath(); // ex: "auth/login" ou "ping"
    String method = ctx.getMethod();

    // Autoriser OPTIONS (si un jour tu ajoutes CORS) + endpoints publics
    if ("OPTIONS".equalsIgnoreCase(method)) return;
    if (path.equals("ping") || path.equals("auth/login")) return;

    String header = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith("Bearer ")) {
      ctx.abortWith(jakarta.ws.rs.core.Response.status(401).entity("Missing Bearer token").build());
      return;
    }

    String token = header.substring("Bearer ".length()).trim();
    var sessionOpt = AppContext.sessions().findByToken(token);
    if (sessionOpt.isEmpty()) {
      ctx.abortWith(jakarta.ws.rs.core.Response.status(401).entity("Invalid/expired token").build());
      return;
    }

    var session = sessionOpt.get();
    var user = AppContext.users().findById(session.getUserId()).orElse(null);
    if (user == null) {
      ctx.abortWith(jakarta.ws.rs.core.Response.status(401).entity("Invalid token user").build());
      return;
    }

    var principal = new ma.clinique.api.security.AuthPrincipal(
        user.getId(), user.getUsername(), session.getRole()
    );

    boolean secure = ctx.getUriInfo().getRequestUri().getScheme().equals("https");
    ctx.setSecurityContext(new ma.clinique.api.security.SecurityContextImpl(principal, secure));

  }
}
