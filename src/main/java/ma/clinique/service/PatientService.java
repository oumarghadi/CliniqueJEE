package ma.clinique.service;

import java.util.List;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import ma.clinique.model.Patient;
import ma.clinique.repo.interfaces.PatientRepository;

public class PatientService {

  private final PatientRepository repo;

  public PatientService(PatientRepository repo) {
    this.repo = repo;
  }

  public Patient create(String firstName, String lastName, String phone, String birthDate) {
    if (firstName == null || firstName.isBlank()) throw new BadRequestException("firstName required");
    if (lastName == null || lastName.isBlank()) throw new BadRequestException("lastName required");
    if (phone == null || phone.isBlank()) throw new BadRequestException("phone required");

    Patient p = new Patient();
    p.setFirstName(firstName);
    p.setLastName(lastName);
    p.setPhone(phone);
    p.setBirthDate(birthDate); // peut être null
    p.setActive(true);

    return repo.save(p);
  }

  public Patient get(long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
  }

  public List<Patient> list() {
    return repo.findAll();
  }

  public Patient update(long id, String firstName, String lastName, String phone, String birthDate, Boolean active) {
    Patient p = get(id);

    // on met à jour seulement si ce n'est pas null
    if (firstName != null) p.setFirstName(firstName);
    if (lastName != null) p.setLastName(lastName);
    if (phone != null) p.setPhone(phone);
    if (birthDate != null) p.setBirthDate(birthDate);
    if (active != null) p.setActive(active.booleanValue());

    return repo.save(p);
  }

  public void delete(long id) {
    boolean ok = repo.delete(id);
    if (!ok) throw new NotFoundException("Patient not found");
  }
}
