package ma.clinique.service;

import java.security.SecureRandom;
import java.util.Base64;

import ma.clinique.api.security.Session;
import ma.clinique.model.User;
import ma.clinique.repo.interfaces.SessionRepository;
import ma.clinique.repo.interfaces.UserRepository;
import ma.clinique.util.PasswordHasher;

public class AuthService {
  private final UserRepository users;
  private final SessionRepository sessions;

  // 8h
  private final long ttlMs = 8L * 60 * 60 * 1000;

  public AuthService(UserRepository users, SessionRepository sessions) {
    this.users = users;
    this.sessions = sessions;
  }

  public Session login(String username, String passwordRaw) {
    User u = users.findByUsername(username)
        .filter(User::isActive)
        .orElseThrow(() -> new RuntimeException("Invalid credentials"));

    String hash = PasswordHasher.sha256Base64(passwordRaw);
    if (!hash.equals(u.getPasswordHash())) {
      throw new RuntimeException("Invalid credentials");
    }

    String token = generateToken();
    Session s = new Session(token, u.getId(), u.getRole(), System.currentTimeMillis() + ttlMs);
    return sessions.save(s);
  }

  public void logout(String token) {
    sessions.delete(token);
  }

  private String generateToken() {
    byte[] b = new byte[32];
    new SecureRandom().nextBytes(b);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(b);
  }
}
