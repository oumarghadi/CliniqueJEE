package ma.clinique.repo.interfaces;

import java.util.List;
import java.util.Optional;
import ma.clinique.model.Patient;

public interface PatientRepository {
  Patient save(Patient p);
  Optional<Patient> findById(long id);
  List<Patient> findAll();
  boolean delete(long id);
}
