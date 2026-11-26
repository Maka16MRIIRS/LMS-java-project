import java.util.ArrayList;
import java.util.Scanner;

// Non-public helper class
class Student {
    private String rollNumber;
    private String name;
    private String[] subjects;
    private double[] marks;

    public Student(String rollNumber, String name, String[] subjects, double[] marks) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.subjects = subjects;
        this.marks = marks;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setMarks(double[] marks) {
        this.marks = marks;
    }

    // Helper method to get grade symbol for a single mark (10-point scale)
    private String getGradeSymbol(double mark) {
        if (mark >= 90) return "O";
        else if (mark >= 80) return "A+";
        else if (mark >= 70) return "A";
        else if (mark >= 60) return "B+";
        else if (mark >= 50) return "B";
        else if (mark >= 40) return "C";
        else return "F";
    }

    // Helper method to convert mark to 10.0 grade points
    private double getGradePoint(double mark) {
        if (mark >= 90) return 10.0;      // O
        else if (mark >= 80) return 9.0;  // A+
        else if (mark >= 70) return 8.0;  // A
        else if (mark >= 60) return 7.0;  // B+
        else if (mark >= 50) return 6.0;  // B
        else if (mark >= 40) return 5.0;  // C
        else return 0.0;                  // F
    }

    // Calculate overall GPA
    public double calculateGPA() {
        double totalPoints = 0;
        for (double mark : marks) {
            totalPoints += getGradePoint(mark);
        }
        return totalPoints / marks.length;
    }

    public void displayReport() {
        System.out.printf("Roll No: %-12s | Name: %-15s | GPA: %.2f%n", rollNumber, name, calculateGPA());
        System.out.print("   Subjects & Grades: ");
        for (int i = 0; i < subjects.length; i++) {
            System.out.printf("%s [%s]", subjects[i], getGradeSymbol(marks[i]));
            if (i < subjects.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\n------------------------------------------------------------------");
    }
}

// Public class (Save file as proj.java)
public class proj {
    private static final ArrayList<Student> studentList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=== Student Grade Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by Roll Number");
            System.out.println("4. Update Student Marks");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left by nextInt()

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> searchStudent();
                case 4 -> updateMarks();
                case 5 -> System.out.println("Exiting system. Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void addStudent() {
        System.out.print("Enter Student Roll Number: ");
        String rollNumber = scanner.nextLine();

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.print("How many subjects does the student have? ");
        int numSubjects = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String[] subjects = new String[numSubjects];
        double[] marks = new double[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter name for Subject " + (i + 1) + ": ");
            subjects[i] = scanner.nextLine();

            System.out.print("Enter marks for " + subjects[i] + " (out of 100): ");
            marks[i] = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
        }

        studentList.add(new Student(rollNumber, name, subjects, marks));
        System.out.println("Student record added successfully!");
    }

    private static void viewAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("\n--- Student Grade Report ---");
        for (Student s : studentList) {
            s.displayReport();
        }
    }

    private static void searchStudent() {
        System.out.print("Enter Student Roll Number to search: ");
        String searchRoll = scanner.nextLine();

        for (Student s : studentList) {
            if (s.getRollNumber().equalsIgnoreCase(searchRoll)) {
                System.out.println("\nStudent Found:");
                s.displayReport();
                return;
            }
        }
        System.out.println("Student with Roll Number " + searchRoll + " not found.");
    }

    private static void updateMarks() {
        System.out.print("Enter Student Roll Number to update marks: ");
        String searchRoll = scanner.nextLine();

        for (Student s : studentList) {
            if (s.getRollNumber().equalsIgnoreCase(searchRoll)) {
                String[] existingSubjects = s.getSubjects();
                double[] newMarks = new double[existingSubjects.length];
                
                for (int i = 0; i < existingSubjects.length; i++) {
                    System.out.print("Enter new marks for " + existingSubjects[i] + ": ");
                    newMarks[i] = scanner.nextDouble();
                }
                scanner.nextLine(); // Consume newline
                s.setMarks(newMarks);
                System.out.println("Marks updated successfully!");
                return;
            }
        }
        System.out.println("Student with Roll Number " + searchRoll + " not found.");
    }
}
