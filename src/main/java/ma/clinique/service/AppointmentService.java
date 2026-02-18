package ma.clinique.service;

import java.util.List;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import ma.clinique.model.Appointment;
import ma.clinique.model.Patient;
import ma.clinique.model.User;
import ma.clinique.model.enums.AppointmentStatus;
import ma.clinique.model.enums.Role;
import ma.clinique.repo.interfaces.AppointmentRepository;
import ma.clinique.repo.interfaces.PatientRepository;
import ma.clinique.repo.interfaces.UserRepository;

public class AppointmentService {

  private final AppointmentRepository repo;
  private final PatientRepository patients;
  private final UserRepository users;

  public AppointmentService(AppointmentRepository repo, PatientRepository patients, UserRepository users) {
    this.repo = repo;
    this.patients = patients;
    this.users = users;
  }

  public Appointment create(long patientId, long doctorId, String startAt, String reason) {
    Patient p = patients.findById(patientId).orElseThrow(() -> new BadRequestException("patientId not found"));

    User doc = users.findById(doctorId).orElseThrow(() -> new BadRequestException("doctorId not found"));
    if (doc.getRole() != Role.DOCTOR) throw new BadRequestException("doctorId must be a DOCTOR");
    if (!doc.isActive()) throw new BadRequestException("doctor is not active");

    if (startAt == null || startAt.isBlank()) throw new BadRequestException("startAt required");
    if (reason == null || reason.isBlank()) throw new BadRequestException("reason required");

    Appointment a = new Appointment();
    a.setPatientId(p.getId());
    a.setDoctorId(doc.getId());
    a.setStartAt(startAt);
    a.setReason(reason);
    a.setStatus(AppointmentStatus.PLANNED);

    return repo.save(a);
  }

  public List<Appointment> listAll() {
    return repo.findAll();
  }

  public List<Appointment> listByDoctor(long doctorUserId) {
    return repo.findByDoctorUserId(doctorUserId);
  }

  public Appointment get(long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
  }

  public Appointment getFor(Role role, long currentUserId, long id) {
    Appointment a = get(id);
    if (role == Role.ADMIN) return a;
    if (role == Role.DOCTOR && a.getDoctorId() == currentUserId) return a;
    throw new ForbiddenException("Forbidden");
  }

  public Appointment updateAsAdmin(long id, Long patientId, Long doctorId, String startAt, String reason, String status) {
    Appointment a = get(id);

    if (patientId != null) {
      patients.findById(patientId).orElseThrow(() -> new BadRequestException("patientId not found"));
      a.setPatientId(patientId);
    }

    if (doctorId != null) {
      User doc = users.findById(doctorId).orElseThrow(() -> new BadRequestException("doctorId not found"));
      if (doc.getRole() != Role.DOCTOR) throw new BadRequestException("doctorId must be a DOCTOR");
      a.setDoctorId(doctorId);
    }

    if (startAt != null) a.setStartAt(startAt);
    if (reason != null) a.setReason(reason);

    if (status != null) {
      a.setStatus(parseStatus(status));
    }

    return repo.save(a);
  }

  public Appointment updateStatusAsDoctor(long currentDoctorId, long id, String status) {
    Appointment a = get(id);
    if (a.getDoctorId() != currentDoctorId) throw new ForbiddenException("Forbidden");
    if (status == null) throw new BadRequestException("status required");
    a.setStatus(parseStatus(status));
    return repo.save(a);
  }

  public void delete(long id) {
    boolean ok = repo.delete(id);
    if (!ok) throw new NotFoundException("Appointment not found");
  }

  private AppointmentStatus parseStatus(String s) {
    try {
      return AppointmentStatus.valueOf(s.trim().toUpperCase());
    } catch (Exception e) {
      throw new BadRequestException("Invalid status");
    }
  }
}
