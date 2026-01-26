package ma.clinique.api.filters;

import java.io.IOException;
import java.util.Arrays;

import jakarta.annotation.Priority;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.SecurityContext;

import ma.clinique.api.security.AuthPrincipal;
import ma.clinique.model.enums.Role;

@Priority(Priorities.AUTHORIZATION)
public class RoleFilter implements ContainerRequestFilter {

  private final Role[] allowed;

  public RoleFilter(Role[] allowed) {
    this.allowed = allowed;
  }

  @Override
  public void filter(ContainerRequestContext ctx) throws IOException {
    SecurityContext sc = ctx.getSecurityContext();
    if (sc == null || sc.getUserPrincipal() == null) {
      throw new NotAuthorizedException("Bearer token required");
    }

    AuthPrincipal p = (AuthPrincipal) sc.getUserPrincipal();
    boolean ok = Arrays.stream(allowed).anyMatch(r -> r == p.getRole());
    if (!ok) throw new ForbiddenException("Forbidden");
  }
}
