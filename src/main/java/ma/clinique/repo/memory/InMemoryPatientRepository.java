package ma.clinique.repo.memory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import ma.clinique.model.Patient;
import ma.clinique.repo.interfaces.PatientRepository;

public class InMemoryPatientRepository implements PatientRepository {

  private final Map<Long, Patient> store = new ConcurrentHashMap<>();
  private final AtomicLong seq = new AtomicLong(0);

  @Override
  public Patient save(Patient p) {
    if (p.getId() <= 0) p.setId(seq.incrementAndGet());
    store.put(p.getId(), p);
    return p;
  }

  @Override
  public Optional<Patient> findById(long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public List<Patient> findAll() {
    return new ArrayList<>(store.values());
  }

  @Override
  public boolean delete(long id) {
    return store.remove(id) != null;
  }
}
