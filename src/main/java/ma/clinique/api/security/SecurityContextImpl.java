package ma.clinique.api.security;

import java.security.Principal;
import jakarta.ws.rs.core.SecurityContext;

public class SecurityContextImpl implements SecurityContext {
  private final AuthPrincipal principal;
  private final boolean secure;

  public SecurityContextImpl(AuthPrincipal principal, boolean secure) {
    this.principal = principal;
    this.secure = secure;
  }

  @Override public Principal getUserPrincipal() { return principal; }
  @Override public boolean isUserInRole(String role) {
    return principal != null && principal.getRole().name().equals(role);
  }
  @Override public boolean isSecure() { return secure; }
  @Override public String getAuthenticationScheme() { return "Bearer"; }
}
