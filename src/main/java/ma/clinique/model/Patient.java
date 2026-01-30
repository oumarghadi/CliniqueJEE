package ma.clinique.model;

public class Patient {
  private long id;
  private String firstName;
  private String lastName;
  private String phone;
  private String birthDate; // ISO: "YYYY-MM-DD"
  private boolean active = true;

  public Patient() {}

  public Patient(long id, String firstName, String lastName, String phone, String birthDate) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.birthDate = birthDate;
  }

  public long getId() { return id; }
  public void setId(long id) { this.id = id; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public String getBirthDate() { return birthDate; }
  public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

  public boolean isActive() { return active; }
  public void setActive(boolean active) { this.active = active; }
}
