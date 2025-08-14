import java.io.*;
import java.util.*;

class Student implements Serializable {
    private String name;
    private int marks;

    public Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public int getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        return name + " - Marks: " + marks;
    }
}

public class Main {
    private static final String FILE_NAME = "students.dat";
    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        loadStudents();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Student Grade Management ===");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Calculate Average Marks");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter marks: ");
                    int marks = sc.nextInt();
                    sc.nextLine();
                    students.add(new Student(name, marks));
                    saveStudents();
                    break;
                case 2:
                    students.sort(Comparator.comparing(Student::getName));
                    for (Student s : students) {
                        System.out.println(s);
                    }
                    break;
                case 3:
                    if (students.isEmpty()) {
                        System.out.println("No students available.");
                    } else {
                        double avg = students.stream().mapToInt(Student::getMarks).average().orElse(0);
                        System.out.println("Average Marks: " + avg);
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    saveStudents();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    private static void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            students = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }
}
