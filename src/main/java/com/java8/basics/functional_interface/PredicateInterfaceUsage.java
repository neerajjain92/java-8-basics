package com.java8.basics.functional_interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.lang.System.lineSeparator;
import static java.lang.System.out;

public class PredicateInterfaceUsage {

    public static void main(String[] args) {

        // 1st Example
        filterStudentsUsingPredicate();
    }

    private static void filterStudentsUsingPredicate() {
        List<Student> studentsInClass = Arrays.asList(
                new Student("Neeraj Jain", 27, 101, GENDER.MALE),
                new Student("Jasmine Sandlas", 30, 102, GENDER.FEMALE),
                new Student("Neha Kakkar", 24, 103, GENDER.FEMALE),
                new Student("Sagar Batra", 28, 104, GENDER.MALE),
                new Student("Jitendra Saini", 25, 105, GENDER.MALE)
        );

        // All Students
        log("All Students");
        out.println(studentsInClass);

        // All Males greater than 25 years of age
        log("All Males greater than 25 years of age");
        out.println(filterStudent(studentsInClass, (Student student) -> student.gender == GENDER.MALE && student.age > 25));

        // All Females in Class
        log("All Females in Class");
        out.println(filterStudent(studentsInClass, (Student student) -> student.gender == GENDER.FEMALE));

        // Student with Roll Number 105
        log("Student with Roll Number 105");
        out.println(filterStudent(studentsInClass, (Student student) -> student.rollNumber == 105));

        // Now let's see basic default methods in Predicate Interface
        // and, or, negate

        log("Students which are either female or having age greater than 27");
        Predicate<Student> predicate1 = (Student student) -> student.gender == GENDER.FEMALE;
        Predicate<Student> predicate2 = (Student student) -> student.age > 27;
        out.println(filterStudent(studentsInClass, predicate1.or(predicate2)));

        log("Students which are male and having age greater than 27");
        predicate1 = (Student student) -> student.gender == GENDER.MALE;
        predicate2 = (Student student) -> student.age > 27;
        out.println(filterStudent(studentsInClass, predicate1.and(predicate2)));

        log("Negation of last predicate");
        out.println(filterStudent(studentsInClass, predicate1.negate()));

    }

    private static void log(String message) {
        System.out.println("===============>>>>>" + message + "<<<<<<=============");
    }

    private static List<Student> filterStudent(List<Student> students, Predicate<Student> predicate) {
        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : students) {
            if (predicate.test(student)) {
                filteredStudents.add(student);
            }
        }
        return filteredStudents;
    }

    static class Student {
        String name;
        int age;
        int rollNumber;
        GENDER gender;

        public Student(String name, int age, int rollNumber, GENDER gender) {
            this.name = name;
            this.age = age;
            this.rollNumber = rollNumber;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return name + " ::=> [" + rollNumber + " , " + age + " , " + gender + "] ";
        }
    }

    private enum GENDER {
        FEMALE, MALE
    }
}
