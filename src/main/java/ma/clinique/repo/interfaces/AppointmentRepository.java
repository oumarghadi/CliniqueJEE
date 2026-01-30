package ma.clinique.repo.interfaces;

import java.util.List;
import java.util.Optional;
import ma.clinique.model.Appointment;

public interface AppointmentRepository {
  Appointment save(Appointment a);
  Optional<Appointment> findById(long id);
  List<Appointment> findAll();
  List<Appointment> findByDoctorId(long doctorId);
  boolean delete(long id);
}
