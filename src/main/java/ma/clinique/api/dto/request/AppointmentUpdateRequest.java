package ma.clinique.api.dto.request;

public class AppointmentUpdateRequest {
  public Long patientId;
  public Long doctorId;
  public String startAt;
  public String reason;
  public String status; // "PLANNED" | "DONE" | "CANCELED"
}
