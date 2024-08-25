import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private String name;
    private String specialty;
    private List<LocalDateTime> availability;
    private List<String> reviews;
    private double rating;

    public Doctor(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
        this.availability = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.rating = 0;
    }

    public void addAvailability(LocalDateTime time) {
        availability.add(time);
    }

    public void removeAvailability(LocalDateTime time) {
        availability.remove(time);
    }

    public boolean isAvailable(LocalDateTime time) {
        return availability.contains(time);
    }

    public List<LocalDateTime> getAvailability() {
        return new ArrayList<>(availability); // Return a copy to avoid modification
    }

    public void addReview(String review, double rating) {
        reviews.add(review);
        this.rating = (this.rating * (reviews.size() - 1) + rating) / reviews.size();
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public double getRating() {
        return rating;
    }

    public void showReviews() {
        System.out.println("Reviews for Dr. " + name + ":");
        for (String review : reviews) {
            System.out.println("- " + review);
        }
        System.out.println("Overall Rating: " + rating + "/5");
    }
}
