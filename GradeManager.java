
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GradeManager {

    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n==================================");
            System.out.println(" STUDENT GRADE MANAGEMENT SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Add Student");
            System.out.println("2. Record Marks");
            System.out.println("3. Class Average & Median");
            System.out.println("4. Generate Report Cards");
            System.out.println("5. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    recordMarks();
                    break;

                case 3:
                    calculateStatistics();
                    break;

                case 4:
                    generateReports();
                    break;

                case 5:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);

        sc.close();
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);

            try {
                double value = Double.parseDouble(sc.nextLine().trim());

                if (Double.isFinite(value)) {
                    return value;
                }

                System.out.println("Enter a finite number.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static Student findStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    private static void addStudent() {
        int id = readInt("Enter Student ID: ");

        if (id <= 0) {
            System.out.println("ID must be positive.");
            return;
        }

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine().trim();

        try {
            Student student = new Student(id, name);
            students.add(student);
            System.out.println("Student added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void recordMarks() {
        int id = readInt("Enter Student ID: ");
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Subject Name: ");
        String subject = sc.nextLine().trim();

        double marks = readDouble("Enter Marks (0-100): ");

        try {
            student.setGrade(subject, marks);
            System.out.println("Marks recorded successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void calculateStatistics() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }

        double[] averages = new double[students.size()];
        double total = 0;

        for (int i = 0; i < students.size(); i++) {
            averages[i] = students.get(i).getAverage();
            total += averages[i];
        }

        Arrays.sort(averages);

        double classAverage = total / students.size();
        int n = averages.length;
        double median;

        if (n % 2 == 0) {
            median = (averages[n / 2 - 1] + averages[n / 2]) / 2.0;
        } else {
            median = averages[n / 2];
        }

        System.out.printf("Class Average: %.2f%n", classAverage);
        System.out.printf("Class Median:  %.2f%n", median);
    }

    private static void generateReports() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }

        System.out.println("\n================ REPORT CARDS ================");

        System.out.printf("%-8s %-20s %-12s %-10s %-8s%n",
                "ID", "NAME", "SUBJECTS", "AVERAGE", "GPA");

        System.out.println("---------------------------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-8d %-20s %-12d %-10.2f %-8.2f%n",
                    s.getId(),
                    s.getName(),
                    s.getGrades().size(),
                    s.getAverage(),
                    s.getGPA());

            for (String subject : s.getGrades().keySet()) {
                System.out.printf("    %-20s : %.2f%n",
                        subject, s.getGrades().get(subject));
            }

            System.out.println("---------------------------------------------------------------");
        }
    }
}
