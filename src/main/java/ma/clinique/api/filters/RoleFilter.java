package ma.clinique.api.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.Priority;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.SecurityContext;
import ma.clinique.api.security.AuthPrincipal;
import ma.clinique.model.enums.Role;

@Priority(Priorities.AUTHORIZATION) // après AuthFilter
public class RoleFilter implements ContainerRequestFilter {

  private final Set<Role> allowed;

  public RoleFilter(Role[] roles) {
    this.allowed = new HashSet<>(Arrays.asList(roles));
  }

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    SecurityContext sc = requestContext.getSecurityContext();
    if (sc == null || sc.getUserPrincipal() == null) {
      throw new ForbiddenException("Forbidden");
    }

    AuthPrincipal p = (AuthPrincipal) sc.getUserPrincipal();
    if (!allowed.contains(p.getRole())) {
      throw new ForbiddenException("Forbidden");
    }
  }
}
