package ma.clinique.bootstrap;

import ma.clinique.model.User;
import ma.clinique.model.enums.Role;
import ma.clinique.repo.interfaces.UserRepository;
import ma.clinique.util.PasswordHasher;

public final class DataSeeder {
  private DataSeeder() {}

  public static void seed(UserRepository users) {
    // admin/admin
    users.save(new User(0, "admin", PasswordHasher.sha256Base64("admin"), Role.ADMIN));
  }
}
