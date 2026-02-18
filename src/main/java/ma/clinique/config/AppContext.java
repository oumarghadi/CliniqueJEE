package ma.clinique.config;

import ma.clinique.bootstrap.DataSeeder;

import ma.clinique.repo.interfaces.*;
import ma.clinique.repo.memory.*;

import ma.clinique.service.AuthService;
import ma.clinique.service.PatientService;
import ma.clinique.service.AppointmentService;

public final class AppContext {

  // Auth
  private static final UserRepository userRepo = new InMemoryUserRepository();
  private static final SessionRepository sessionRepo = new InMemorySessionRepository();
  private static final AuthService authService = new AuthService(userRepo, sessionRepo);

  // Patients
  private static final PatientRepository patientRepo = new InMemoryPatientRepository();
  private static final PatientService patientService = new PatientService(patientRepo);

  // Appointments
  private static final AppointmentRepository appointmentRepo = new InMemoryAppointmentRepository();
  private static final AppointmentService appointmentService =
      new AppointmentService(appointmentRepo, patientRepo, userRepo);

  static {
    DataSeeder.seed(userRepo);
  }

  private AppContext() {}

  public static UserRepository users() { return userRepo; }
  public static SessionRepository sessions() { return sessionRepo; }
  public static AuthService auth() { return authService; }

  public static PatientService patients() { return patientService; }
  public static AppointmentService appointments() { return appointmentService; }
}
