package ma.clinique.model;

import ma.clinique.model.enums.AppointmentStatus;

public class Appointment {
  private long id;
  private long patientId;
  private long doctorId;
  private String startAt; // ISO string ex: 2026-02-01T10:00
  private String reason;
  private AppointmentStatus status = AppointmentStatus.PLANNED;

  public long getId() { return id; }
  public void setId(long id) { this.id = id; }

  public long getPatientId() { return patientId; }
  public void setPatientId(long patientId) { this.patientId = patientId; }

  public long getDoctorId() { return doctorId; }
  public void setDoctorId(long doctorId) { this.doctorId = doctorId; }

  public String getStartAt() { return startAt; }
  public void setStartAt(String startAt) { this.startAt = startAt; }

  public String getReason() { return reason; }
  public void setReason(String reason) { this.reason = reason; }

  public AppointmentStatus getStatus() { return status; }
  public void setStatus(AppointmentStatus status) { this.status = status; }
}
