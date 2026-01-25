package ma.clinique.repo.memory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import ma.clinique.api.security.Session;
import ma.clinique.repo.interfaces.SessionRepository;

public class InMemorySessionRepository implements SessionRepository {
  private final ConcurrentHashMap<String, Session> store = new ConcurrentHashMap<>();

  @Override
  public Session save(Session s) {
    store.put(s.getToken(), s);
    return s;
  }

  @Override
  public Optional<Session> findByToken(String token) {
    Session s = store.get(token);
    if (s == null) return Optional.empty();
    if (s.isExpired()) {
      store.remove(token);
      return Optional.empty();
    }
    return Optional.of(s);
  }

  @Override
  public void delete(String token) {
    store.remove(token);
  }
}
