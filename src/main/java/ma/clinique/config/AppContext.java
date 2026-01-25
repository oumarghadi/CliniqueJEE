package ma.clinique.config;

import ma.clinique.bootstrap.DataSeeder;
import ma.clinique.repo.interfaces.SessionRepository;
import ma.clinique.repo.interfaces.UserRepository;
import ma.clinique.repo.memory.InMemorySessionRepository;
import ma.clinique.repo.memory.InMemoryUserRepository;
import ma.clinique.service.AuthService;

public final class AppContext {
  private static final UserRepository userRepo = new InMemoryUserRepository();
  private static final SessionRepository sessionRepo = new InMemorySessionRepository();
  private static final AuthService authService = new AuthService(userRepo, sessionRepo);

  static {
    DataSeeder.seed(userRepo);
  }

  private AppContext() {}

  public static UserRepository users() { return userRepo; }
  public static SessionRepository sessions() { return sessionRepo; }
  public static AuthService auth() { return authService; }
}
