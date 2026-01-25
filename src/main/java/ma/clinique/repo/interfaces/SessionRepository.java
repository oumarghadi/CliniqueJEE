package ma.clinique.repo.interfaces;

import java.util.Optional;
import ma.clinique.api.security.Session;

public interface SessionRepository {
  Session save(Session s);
  Optional<Session> findByToken(String token);
  void delete(String token);
}
