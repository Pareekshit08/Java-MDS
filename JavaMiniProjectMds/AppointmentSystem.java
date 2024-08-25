import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppointmentSystem {
    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<Appointment> appointments;

    public AppointmentSystem() {
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
        appointments = new ArrayList<>();

        // Adding some predefined doctors
        Doctor doctor1 = new Doctor("Dr. Smith", "Cardiology");
        Doctor doctor2 = new Doctor("Dr. Johnson", "Neurology");
        Doctor doctor3 = new Doctor("Dr. Williams", "Orthopedics");
        Doctor doctor4 = new Doctor("Dr. Brown", "Pediatrics");

        doctors.add(doctor1);
        doctors.add(doctor2);
        doctors.add(doctor3);
        doctors.add(doctor4);

        // Adding predefined availability times
        addPredefinedAvailability(doctor1);
        addPredefinedAvailability(doctor2);
        addPredefinedAvailability(doctor3);
        addPredefinedAvailability(doctor4);
    }

    private void addPredefinedAvailability(Doctor doctor) {
        doctor.addAvailability(LocalDateTime.of(2024, Month.AUGUST, 26, 9, 0));
        doctor.addAvailability(LocalDateTime.of(2024, Month.AUGUST, 26, 10, 0));
        doctor.addAvailability(LocalDateTime.of(2024, Month.AUGUST, 27, 14, 0));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to the Medical Appointment System");
            System.out.println("1. Login as Patient");
            System.out.println("2. Login as Doctor");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    patientMenu(scanner);
                    break;
                case 2:
                    doctorMenu(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void patientMenu(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Patient patient = new Patient(name, age);
        patients.add(patient);

        System.out.println("Patient registered successfully!");

        System.out.println("Available Doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            System.out.println((i + 1) + ". " + doctor.getName() + " (" + doctor.getSpecialty() + ")");
        }

        System.out.print("Choose a doctor by number: ");
        int doctorChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (doctorChoice < 1 || doctorChoice > doctors.size()) {
            System.out.println("Invalid choice. Returning to main menu.");
            return;
        }

        Doctor doctor = doctors.get(doctorChoice - 1);

        // Display doctor's availability
        System.out.println("Available times for Dr. " + doctor.getName() + ":");
        List<LocalDateTime> availability = doctor.getAvailability();
        for (LocalDateTime time : availability) {
            System.out.println(time);
        }

        LocalDateTime appointmentTime = null;
        while (appointmentTime == null) {
            System.out.print("Enter appointment date and time (YYYY-MM-DDTHH:MM): ");
            String dateTimeString = scanner.nextLine();
            try {
                appointmentTime = LocalDateTime.parse(dateTimeString);
                if (!doctor.isAvailable(appointmentTime)) {
                    System.out.println("Doctor is not available at this time. Please choose a different time.");
                    appointmentTime = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid date or time format. Please try again.");
            }
        }

        Appointment appointment = new Appointment(patient, doctor, appointmentTime);

        System.out.print("Do you want to upload a health record? (yes/no): ");
        String healthRecordChoice = scanner.nextLine();
        if (healthRecordChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter health record details: ");
            String healthRecord = scanner.nextLine();
            appointment.setHealthRecord(healthRecord);
        }

        appointments.add(appointment);
        patient.addAppointmentToHistory(appointment);

        System.out.println("Appointment booked successfully!");

        // Allow rating and reviewing the doctor
        System.out.print("Would you like to rate and review the doctor after the appointment? (yes/no): ");
        String reviewChoice = scanner.nextLine();
        if (reviewChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter your rating (1-5): ");
            double rating = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter your review: ");
            String review = scanner.nextLine();
            doctor.addReview(review, rating);
        }

        System.out.print("Would you like to see a summary of your appointment? (yes/no): ");
        String summaryChoice = scanner.nextLine();
        if (summaryChoice.equalsIgnoreCase("yes")) {
            appointment.showSummary();
        }
    }

    private void doctorMenu(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        Doctor doctor = null;
        for (Doctor d : doctors) {
            if (d.getName().equalsIgnoreCase(name)) {
                doctor = d;
                break;
            }
        }

        if (doctor == null) {
            System.out.println("Doctor not found. Please register.");
            System.out.print("Enter your specialty: ");
            String specialty = scanner.nextLine();
        
            doctor = new Doctor(name, specialty);
            doctors.add(doctor);
        
            System.out.println("Doctor registered successfully!");
        }

        System.out.println("Doctor logged in successfully!");

        while (true) {
            System.out.println("Doctor Menu:");
            System.out.println("1. View Appointments");
            System.out.println("2. Manage Availability");
            System.out.println("3. View Reviews");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAppointments(doctor);
                    break;
                case 2:
                    manageAvailability(scanner, doctor);
                    break;
                case 3:
                    doctor.showReviews();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewAppointments(Doctor doctor) {
        List<Appointment> doctorAppointments = appointments.stream()
                .filter(a -> a.getDoctor().equals(doctor))
                .toList();

        if (doctorAppointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("Appointments for Dr. " + doctor.getName() + ":");
            for (Appointment appointment : doctorAppointments) {
                appointment.showSummary();
            }
        }
    }

    private void manageAvailability(Scanner scanner, Doctor doctor) {
        while (true) {
            System.out.println("Manage Availability:");
            System.out.println("1. Add Availability");
            System.out.println("2. Remove Availability");
            System.out.println("3. Back to Doctor Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter available date and time (YYYY-MM-DDTHH:MM): ");
                    String availability = scanner.nextLine();
                    try {
                        doctor.addAvailability(LocalDateTime.parse(availability));
                        System.out.println("Availability added successfully.");
                    } catch (Exception e) {
                        System.out.println("Invalid date or time format. Please try again.");
                    }
                    break;
                case 2:
                    System.out.print("Enter date and time to remove from availability (YYYY-MM-DDTHH:MM): ");
                    String removeAvailability = scanner.nextLine();
                    try {
                        doctor.removeAvailability(LocalDateTime.parse(removeAvailability));
                        System.out.println("Availability removed successfully.");
                    } catch (Exception e) {
                        System.out.println("Invalid date or time format. Please try again.");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        AppointmentSystem system = new AppointmentSystem();
        system.start();
    }
}
