package ma.clinique.config;

import ma.clinique.bootstrap.DataSeeder;
import ma.clinique.repo.interfaces.AppointmentRepository;
import ma.clinique.repo.interfaces.PatientRepository;
import ma.clinique.repo.interfaces.SessionRepository;
import ma.clinique.repo.interfaces.UserRepository;
import ma.clinique.repo.memory.InMemoryAppointmentRepository;
import ma.clinique.repo.memory.InMemoryPatientRepository;
import ma.clinique.repo.memory.InMemorySessionRepository;
import ma.clinique.repo.memory.InMemoryUserRepository;
import ma.clinique.service.AppointmentService;
import ma.clinique.service.AuthService;
import ma.clinique.service.PatientService;

public final class AppContext {

  // -------------------------
  // Repositories (InMemory)
  // -------------------------
  private static final UserRepository userRepo = new InMemoryUserRepository();
  private static final SessionRepository sessionRepo = new InMemorySessionRepository();
  private static final PatientRepository patientRepo = new InMemoryPatientRepository();
  private static final AppointmentRepository appointmentRepo = new InMemoryAppointmentRepository();

  // -------------------------
  // Services
  // -------------------------
  private static final AuthService authService = new AuthService(userRepo, sessionRepo);
  private static final PatientService patientService = new PatientService(patientRepo);
  private static final AppointmentService appointmentService =
      new AppointmentService(appointmentRepo, patientRepo, userRepo);

  // -------------------------
  // Seed data (users admin/doc, etc.)
  // -------------------------
  static {
    DataSeeder.seed(userRepo);
  }

  private AppContext() {}

  // -------------------------
  // Accessors
  // -------------------------
  public static UserRepository users() { return userRepo; }
  public static SessionRepository sessions() { return sessionRepo; }
  public static PatientRepository patientRepo() { return patientRepo; } // optionnel
  public static AppointmentRepository appointmentRepo() { return appointmentRepo; } // optionnel

  public static AuthService auth() { return authService; }
  public static PatientService patients() { return patientService; }
  public static AppointmentService appointments() { return appointmentService; }
}
