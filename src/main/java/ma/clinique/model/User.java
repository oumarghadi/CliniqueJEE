package ma.clinique.model;

import ma.clinique.model.enums.Role;

public class User {
  private long id;
  private String username;
  private String passwordHash;
  private Role role;
  private boolean active = true;

  public User() {}

  public User(long id, String username, String passwordHash, Role role) {
    this.id = id;
    this.username = username;
    this.passwordHash = passwordHash;
    this.role = role;
  }

  public long getId() { return id; }
  public void setId(long id) { this.id = id; }

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

  public Role getRole() { return role; }
  public void setRole(Role role) { this.role = role; }

  public boolean isActive() { return active; }
  public void setActive(boolean active) { this.active = active; }
}
