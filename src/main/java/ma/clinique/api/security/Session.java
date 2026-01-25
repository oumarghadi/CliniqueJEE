package ma.clinique.api.security;

import ma.clinique.model.enums.Role;

public class Session {
  private final String token;
  private final long userId;
  private final Role role;
  private final long expiresAtEpochMs;

  public Session(String token, long userId, Role role, long expiresAtEpochMs) {
    this.token = token;
    this.userId = userId;
    this.role = role;
    this.expiresAtEpochMs = expiresAtEpochMs;
  }

  public String getToken() { return token; }
  public long getUserId() { return userId; }
  public Role getRole() { return role; }
  public long getExpiresAtEpochMs() { return expiresAtEpochMs; }

  public boolean isExpired() {
    return System.currentTimeMillis() > expiresAtEpochMs;
  }
}
