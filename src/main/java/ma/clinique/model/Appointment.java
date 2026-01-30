package ma.clinique.model;

import ma.clinique.model.enums.AppointmentStatus;

public class Appointment {
  private long id;
  private long patientId;
  private long doctorId;
  private String startAt; // "YYYY-MM-DDTHH:mm"
  private String reason;
  private AppointmentStatus status = AppointmentStatus.PLANNED;

  public Appointment() {}

  public Appointment(long id, long patientId, long doctorId, String startAt, String reason) {
    this.id = id;
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.startAt = startAt;
    this.reason = reason;
    this.status = AppointmentStatus.PLANNED;
  }

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
