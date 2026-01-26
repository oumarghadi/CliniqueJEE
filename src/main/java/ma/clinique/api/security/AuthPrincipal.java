package ma.clinique.api.security;

import java.security.Principal;
import ma.clinique.model.enums.Role;

public class AuthPrincipal implements Principal {
  private final long userId;
  private final String username;
  private final Role role;

  public AuthPrincipal(long userId, String username, Role role) {
    this.userId = userId;
    this.username = username;
    this.role = role;
  }

  @Override public String getName() { return username; }
  public long getUserId() { return userId; }
  public Role getRole() { return role; }
}
