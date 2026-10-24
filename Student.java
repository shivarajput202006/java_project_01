
import java.util.HashMap;
import java.util.Map;

public class Student {

    private int id;
    private String name;
    private HashMap<String, Double> grades;

    public Student(int id, String name) {
        setId(id);
        setName(name);
        grades = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "Student ID must be positive."
            );
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Student name cannot be empty."
            );
        }
        this.name = name.trim();
    }

    public void setGrade(String subject, double marks) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Subject cannot be empty."
            );
        }

        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException(
                "Marks must be between 0 and 100."
            );
        }

        grades.put(subject.trim(), marks);
    }

    public Map<String, Double> getGrades() {
        return new HashMap<>(grades);
    }

    public double getAverage() {
        if (grades.isEmpty()) {
            return 0.0;
        }

        double total = 0;

        for (double marks : grades.values()) {
            total += marks;
        }

        return total / grades.size();
    }

    public double getGPA() {
        return getAverage() / 10.0;
    }
}
