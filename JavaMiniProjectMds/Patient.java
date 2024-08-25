import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private int age;
    private List<Appointment> appointmentHistory;

    public Patient(String name, int age) {
        this.name = name;
        this.age = age;
        this.appointmentHistory = new ArrayList<>();
    }

    public void addAppointmentToHistory(Appointment appointment) {
        appointmentHistory.add(appointment);
    }

    public List<Appointment> getAppointmentHistory() {
        return new ArrayList<>(appointmentHistory);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
