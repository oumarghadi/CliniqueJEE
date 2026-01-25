package ma.clinique.repo.interfaces;

import java.util.Optional;
import ma.clinique.model.User;

public interface UserRepository {
  User save(User u);
  Optional<User> findById(long id);
  Optional<User> findByUsername(String username);
}
