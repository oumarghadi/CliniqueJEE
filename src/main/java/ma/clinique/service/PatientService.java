package ma.clinique.service;

import java.time.LocalDate;
import java.util.List;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import ma.clinique.model.Patient;
import ma.clinique.repo.interfaces.PatientRepository;

public class PatientService {
  private final PatientRepository patients;

  public PatientService(PatientRepository patients) {
    this.patients = patients;
  }

  public Patient create(String firstName, String lastName, String phone, String birthDate) {
    validateCreate(firstName, lastName, birthDate);
    Patient p = new Patient(0, firstName.trim(), lastName.trim(), phone, birthDate);
    return patients.save(p);
  }

  public Patient update(long id, String firstName, String lastName, String phone, String birthDate, Boolean active) {
    Patient p = patients.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
    if (firstName != null) p.setFirstName(firstName.trim());
    if (lastName != null) p.setLastName(lastName.trim());
    if (phone != null) p.setPhone(phone);
    if (birthDate != null) {
      parseDate(birthDate);
      p.setBirthDate(birthDate);
    }
    if (active != null) p.setActive(active);
    return patients.save(p);
  }

  public Patient get(long id) {
    return patients.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
  }

  public List<Patient> list() {
    return patients.findAll();
  }

  public void delete(long id) {
    patients.delete(id);
  }

  private void validateCreate(String firstName, String lastName, String birthDate) {
    if (firstName == null || firstName.isBlank()) throw new BadRequestException("firstName required");
    if (lastName == null || lastName.isBlank()) throw new BadRequestException("lastName required");
    if (birthDate == null || birthDate.isBlank()) throw new BadRequestException("birthDate required");
    parseDate(birthDate);
  }

  private void parseDate(String s) {
    try { LocalDate.parse(s); }
    catch (Exception e) { throw new BadRequestException("birthDate must be YYYY-MM-DD"); }
  }
}
