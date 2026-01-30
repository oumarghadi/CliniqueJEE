package ma.clinique.service;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import ma.clinique.model.Appointment;
import ma.clinique.model.enums.AppointmentStatus;
import ma.clinique.repo.interfaces.AppointmentRepository;
import ma.clinique.repo.interfaces.PatientRepository;
import ma.clinique.repo.interfaces.UserRepository;

public class AppointmentService {
  private final AppointmentRepository appointments;
  private final PatientRepository patients;
  private final UserRepository users;

  public AppointmentService(AppointmentRepository appointments, PatientRepository patients, UserRepository users) {
    this.appointments = appointments;
    this.patients = patients;
    this.users = users;
  }

  public Appointment create(long patientId, long doctorId, String startAt, String reason) {
    if (reason == null || reason.isBlank()) throw new BadRequestException("reason required");
    parseDateTime(startAt);

    patients.findById(patientId).orElseThrow(() -> new BadRequestException("patientId invalid"));
    users.findById(doctorId).orElseThrow(() -> new BadRequestException("doctorId invalid"));

    Appointment a = new Appointment(0, patientId, doctorId, startAt, reason.trim());
    return appointments.save(a);
  }

  public Appointment get(long id) {
    return appointments.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
  }

  public List<Appointment> listAll() {
    return appointments.findAll();
  }

  public List<Appointment> listByDoctor(long doctorId) {
    return appointments.findByDoctorId(doctorId);
  }

  public Appointment updateStatus(long id, AppointmentStatus status) {
    Appointment a = appointments.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
    a.setStatus(status);
    return appointments.save(a);
  }

  public void delete(long id) {
    appointments.delete(id);
  }

  private void parseDateTime(String s) {
    if (s == null || s.isBlank()) throw new BadRequestException("startAt required");
    try { LocalDateTime.parse(s); }
    catch (Exception e) { throw new BadRequestException("startAt must be YYYY-MM-DDTHH:mm"); }
  }
}
