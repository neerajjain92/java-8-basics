package com.java8.basics;

class Employee {
    int empId;
    String firstName;
    String lastName;
    int age;

    public Employee(int empId, String firstName, String lastName, int age) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
