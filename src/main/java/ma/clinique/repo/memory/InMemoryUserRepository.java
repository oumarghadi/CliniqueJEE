package ma.clinique.repo.memory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import ma.clinique.model.User;
import ma.clinique.repo.interfaces.UserRepository;

public class InMemoryUserRepository implements UserRepository {
  private final ConcurrentHashMap<Long, User> store = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, Long> byUsername = new ConcurrentHashMap<>();
  private final AtomicLong seq = new AtomicLong(0);

  @Override
  public User save(User u) {
    if (u.getId() == 0) {
      u.setId(seq.incrementAndGet());
    }
    store.put(u.getId(), u);
    byUsername.put(u.getUsername(), u.getId());
    return u;
  }

  @Override
  public Optional<User> findById(long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public Optional<User> findByUsername(String username) {
    Long id = byUsername.get(username);
    if (id == null) return Optional.empty();
    return findById(id);
  }
}
