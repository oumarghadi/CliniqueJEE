package ma.clinique.api.dto.request;

public class AppointmentCreateRequest {
  public long patientId;
  public long doctorId;
  public String startAt;
  public String reason;
}
