import java.time.LocalDateTime;

public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime time;
    private String healthRecord;

    public Appointment(Patient patient, Doctor doctor, LocalDateTime time) {
        this.patient = patient;
        this.doctor = doctor;
        this.time = time;
        this.healthRecord = "";
    }

    public void setHealthRecord(String healthRecord) {
        this.healthRecord = healthRecord;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getHealthRecord() {
        return healthRecord;
    }

    public void showSummary() {
        System.out.println("Appointment Summary:");
        System.out.println("Patient: " + patient.getName());
        System.out.println("Doctor: " + doctor.getName());
        System.out.println("Time: " + time);
        System.out.println("Health Record: " + (healthRecord.isEmpty() ? "No record uploaded" : healthRecord));
    }
}
