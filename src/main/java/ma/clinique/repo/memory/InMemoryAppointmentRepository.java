package ma.clinique.repo.memory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import ma.clinique.model.Appointment;
import ma.clinique.repo.interfaces.AppointmentRepository;

public class InMemoryAppointmentRepository implements AppointmentRepository {

  private final Map<Long, Appointment> store = new ConcurrentHashMap<>();
  private final AtomicLong seq = new AtomicLong(0);

  @Override
  public Appointment save(Appointment a) {
    if (a.getId() <= 0) a.setId(seq.incrementAndGet());
    store.put(a.getId(), a);
    return a;
  }

  @Override
  public Optional<Appointment> findById(long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public List<Appointment> findAll() {
    return new ArrayList<>(store.values());
  }

  @Override
  public List<Appointment> findByDoctorUserId(long doctorUserId) {
    return store.values().stream()
        .filter(x -> x.getDoctorId() == doctorUserId)
        .collect(Collectors.toList());
  }

  @Override
  public boolean delete(long id) {
    return store.remove(id) != null;
  }
}
