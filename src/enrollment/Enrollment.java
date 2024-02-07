package enrollment;

import java.time.LocalDate;
import java.util.ArrayList;

import course.*;
import cursist.*;

public class Enrollment extends course.Course {
    private LocalDate enrollmentDate;
    private String courseName;
    private String cursistEmailAddress;

    public Enrollment() {
        this.enrollmentDate = enrollmentDate;
        this.courseName = courseName;
        this.cursistEmailAddress = cursistEmailAddress;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public String getCursistEmailAddress() {
        return cursistEmailAddress;
    }

    public String getCourseName() {

        return courseName;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public void setCursistEmailAddress(String cursistEmailAddress) {
        this.cursistEmailAddress = cursistEmailAddress;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<String> getCourseNames() {
        courseController courseController = new courseController();
        ArrayList<String> courseNames = courseController.getAllCourses();

        return courseNames;
    }

    public ArrayList<String> getCursistEmailAddresses() {
        CursistController cursistController = new CursistController();
        ArrayList<String> cursistEmailAddresses = cursistController.getAllCursistEmailAddress();
        return cursistEmailAddresses;
    }

}